var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 663, "sl" : 32, "el" : 80, "name" : "AnnotationTests",
    "methods" : [
             {"sl" : 47, "el" : 61, "sc" : 3},  {"sl" : 63, "el" : 72, "sc" : 3},  {"sl" : 74, "el" : 79, "sc" : 3}  ]}
    ,
    {"id" : 663, "sl" : 37, "el" : 37, "name" : "AnnotationTests.A",
    "methods" : [
             ]}
    ,
    {"id" : 663, "sl" : 39, "el" : 39, "name" : "AnnotationTests.B",
    "methods" : [
              ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_2" : {
					  "name" : "checkLeasedAnnotation",
					  "pass" : true,
					  "methods" : [{"sl": 47 },{"sl": 74 },],
					  "statements" : [{"sl": 48 },{"sl": 50 },{"sl": 51 },{"sl": 56 },{"sl": 59 },{"sl": 60 },{"sl": 76 },{"sl": 78 },]
					  },
		"test_14" : {
					  "name" : "checkServiceAnnotation",
					  "pass" : true,
					  "methods" : [{"sl": 47 },{"sl": 63 },],
					  "statements" : [{"sl": 48 },{"sl": 50 },{"sl": 51 },{"sl": 56 },{"sl": 59 },{"sl": 60 },{"sl": 65 },{"sl": 66 },{"sl": 67 },{"sl": 69 },{"sl": 71 },]
					  }
 };

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [  [],   [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 2 , 14   ] ,
  [ 2 , 14   ] ,
  [  ] ,
  [ 2 , 14   ] ,
  [ 2 , 14   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 2 , 14   ] ,
  [  ] ,
  [  ] ,
  [ 2 , 14   ] ,
  [ 2 , 14   ] ,
  [  ] ,
  [  ] ,
  [ 14   ] ,
  [  ] ,
  [ 14   ] ,
  [ 14   ] ,
  [ 14   ] ,
  [  ] ,
  [ 14   ] ,
  [  ] ,
  [ 14   ] ,
  [  ] ,
  [  ] ,
  [ 2   ] ,
  [  ] ,
  [ 2   ] ,
  [  ] ,
  [ 2   ] ,
  [  ] ,
  [  ] 
];
