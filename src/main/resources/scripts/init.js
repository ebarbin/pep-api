db.getCollection('institute').remove({});
db.getCollection('teacher').remove({});
db.getCollection('student').remove({});

db.getCollection('institute').save({name:'Instituto'});
var insti = db.getCollection('institute').findOne({name:'Instituto'})
db.getCollection('teacher').save({documentType:'DNI', documentNumber:'1', instituteId: insti._id.str});

db.getCollection('student').save({documentType:'DNI', documentNumber:'6', instituteId: insti._id.str, courses: []});