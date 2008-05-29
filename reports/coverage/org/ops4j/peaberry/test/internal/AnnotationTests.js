var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 694, "sl" : 33, "el" : 87, "name" : "AnnotationTests",
    "methods" : [
             {"sl" : 49, "el" : 63, "sc" : 3},  {"sl" : 65, "el" : 74, "sc" : 3},  {"sl" : 76, "el" : 80, "sc" : 3},  {"sl" : 82, "el" : 86, "sc" : 3}  ]}
    ,
    {"id" : 694, "sl" : 38, "el" : 38, "name" : "AnnotationTests.A",
    "methods" : [
             ]}
    ,
    {"id" : 694, "sl" : 40, "el" : 40, "name" : "AnnotationTests.B",
    "methods" : [
              ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_3" : {
					  "name" : "checkAnnotationTypes",
					  "pass" : true,
					  "methods" : [{"sl": 82 },],
					  "statements" : [{"sl": 84 },{"sl": 85 },]
					  },
		"test_5" : {
					  "name" : "checkNotEquals",
					  "pass" : true,
					  "methods" : [{"sl": 76 },],
					  "statements" : [{"sl": 78 },{"sl": 79 },]
					  },
		"test_10" : {
					  "name" : "checkServiceAnnotation",
					  "pass" : true,
					  "methods" : [{"sl": 49 },{"sl": 65 },],
					  "statements" : [{"sl": 50 },{"sl": 52 },{"sl": 53 },{"sl": 58 },{"sl": 59 },{"sl": 61 },{"sl": 67 },{"sl": 68 },{"sl": 69 },{"sl": 70 },{"sl": 72 },]
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
  [  ] ,
  [  ] ,
  [ 10   ] ,
  [ 10   ] ,
  [  ] ,
  [ 10   ] ,
  [ 10   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 10   ] ,
  [ 10   ] ,
  [  ] ,
  [ 10   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 10   ] ,
  [  ] ,
  [ 10   ] ,
  [ 10   ] ,
  [ 10   ] ,
  [ 10   ] ,
  [  ] ,
  [ 10   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 5   ] ,
  [  ] ,
  [ 5   ] ,
  [ 5   ] ,
  [  ] ,
  [  ] ,
  [ 3   ] ,
  [  ] ,
  [ 3   ] ,
  [ 3   ] ,
  [  ] ,
  [  ] 
];
