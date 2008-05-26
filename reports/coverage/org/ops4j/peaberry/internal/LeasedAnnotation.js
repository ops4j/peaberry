var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 86, "sl" : 28, "el" : 67, "name" : "LeasedAnnotation",
    "methods" : [
              {"sl" : 33, "el" : 35, "sc" : 3},  {"sl" : 37, "el" : 39, "sc" : 3},  {"sl" : 41, "el" : 43, "sc" : 3},  {"sl" : 45, "el" : 48, "sc" : 3},  {"sl" : 50, "el" : 57, "sc" : 3},  {"sl" : 59, "el" : 66, "sc" : 3}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_21" : {
					  "name" : "checkLeasedAnnotation",
					  "pass" : true,
					  "methods" : [{"sl": 33 },{"sl": 37 },{"sl": 41 },{"sl": 45 },{"sl": 50 },],
					  "statements" : [{"sl": 34 },{"sl": 38 },{"sl": 42 },{"sl": 47 },{"sl": 52 },{"sl": 53 },{"sl": 56 },]
					  },
		"test_20" : {
					  "name" : "testAnnotations",
					  "pass" : true,
					  "methods" : [{"sl": 33 },{"sl": 41 },],
					  "statements" : [{"sl": 34 },{"sl": 42 },]
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
  [ 21 , 20   ] ,
  [ 21 , 20   ] ,
  [  ] ,
  [  ] ,
  [ 21   ] ,
  [ 21   ] ,
  [  ] ,
  [  ] ,
  [ 21 , 20   ] ,
  [ 21 , 20   ] ,
  [  ] ,
  [  ] ,
  [ 21   ] ,
  [  ] ,
  [ 21   ] ,
  [  ] ,
  [  ] ,
  [ 21   ] ,
  [  ] ,
  [ 21   ] ,
  [ 21   ] ,
  [  ] ,
  [  ] ,
  [ 21   ] ,
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
  [  ] 
];
