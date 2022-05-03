package com.example.demo.daoImpl;

import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.example.demo.dao.CourseDao;
import com.example.demo.dto.CourseDTO;
import com.example.demo.model.Course;
import com.example.demo.repository.CourseRepo;
import com.example.demo.request.ListPageRequest;

@Component
public class CourseDaoImpl implements CourseDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private CourseRepo courseRpo;

	@Override
	public Course findCourseById(String id) {

		return courseRpo.findCourseById(id);
	}

	@Override
	public List<Course> findAll() {

		Criteria criteria = Criteria.where("suspended").is(false);
		Query query = Query.query(criteria);
		return mongoTemplate.find(query, Course.class, "CourseInfo");

	}

	@Override
	public Course updateCourse(Course course) {

		Query query = Query.query(Criteria.where("cid").is(course.getCid()).and("suspended").is(false));
		Update update = new Update().set("cname", course.getCname()).set("cdesc", course.getCdesc())
				.set("modifiedAt", new Date()).set("createdBy", course.getCreatedBy());
		return mongoTemplate.findAndModify(query, update, FindAndModifyOptions.options().returnNew(true), Course.class);

	}

	@Override
	public Course deleteCourse(Course course) {

		Query query = Query.query(Criteria.where("cid").is(course.getCid()).and("suspended").is(false));
		Update update = new Update().set("modifiedAt", new Date()).set("suspended", true);
		return mongoTemplate.findAndModify(query, update, FindAndModifyOptions.options().returnNew(true), Course.class);

	}

	@Override
	public List<Course> findPage(ListPageRequest request) {

		Criteria cnm = Criteria.where("cname").regex(request.getSearchText(), "i");
		Criteria cds = Criteria.where("cdesc").regex(request.getSearchText(), "i");

		Query query = new Query(new Criteria().orOperator(cnm, cds).and("suspended").is(false));
		query.skip(request.getPage() * request.getTotalInList()).limit((int) request.getTotalInList());

		String s[] = request.getFields();

		query.fields().include(s).include("_class");

		return mongoTemplate.find(query, Course.class, "CourseInfo");

	}

	@Override
	public List<CourseDTO> findAllCoursesByLookup() {

		AggregationOperation documentId = new AggregationOperation() {
			@Override
			public Document toDocument(AggregationOperationContext context) {

				return new Document("$addFields", new Document("cid", new Document("$toString", "$_id")))
						.append("$match", new Document("suspended", false));
			}
		};

		LookupOperation ss = Aggregation.lookup("StudentInfo", "cid", "courseIds", "students");

		List<CourseDTO> list = mongoTemplate
				.aggregate(Aggregation.newAggregation(documentId, ss), "CourseInfo", CourseDTO.class)
				.getMappedResults();
		// Set<String> ss1=
		// list.stream().map(l->l.getCreatedBy()).collect(Collectors.toSet());
		return list;

	}

	@Override
	public List<Course> findAllCoursesBycreatedBy(String createdBy) {
		Criteria criteria = Criteria.where("createdBy").is(createdBy);
		Query query = Query.query(criteria);
		return mongoTemplate.find(query, Course.class, "CourseInfo");

	}

}
