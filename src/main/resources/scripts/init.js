db.getCollection('institute').remove({});
db.getCollection('teacher').remove({});
db.getCollection('student').remove({});

db.getCollection('institute').save({name:'Instituto'});
var insti = db.getCollection('institute').findOne({name:'Instituto'})
db.getCollection('teacher').save({documentType:'DNI', documentNumber:'1', instituteId: insti._id.toString()});
db.getCollection('teacher').save({documentType:'DNI', documentNumber:'2', instituteId: insti._id.toString()});
db.getCollection('teacher').save({documentType:'DNI', documentNumber:'3', instituteId: insti._id.toString()});
db.getCollection('teacher').save({documentType:'DNI', documentNumber:'4', instituteId: insti._id.toString()});
db.getCollection('teacher').save({documentType:'DNI', documentNumber:'5', instituteId: insti._id.toString()});

db.getCollection('student').save({documentType:'DNI', documentNumber:'6', instituteId: insti._id.toString(), courses: []});
db.getCollection('student').save({documentType:'DNI', documentNumber:'7', instituteId: insti._id.toString(), courses: []});
db.getCollection('student').save({documentType:'DNI', documentNumber:'8', instituteId: insti._id.toString(), courses: []});
db.getCollection('student').save({documentType:'DNI', documentNumber:'9', instituteId: insti._id.toString(), courses: []});
db.getCollection('student').save({documentType:'DNI', documentNumber:'10', instituteId: insti._id.toString(), courses: []});