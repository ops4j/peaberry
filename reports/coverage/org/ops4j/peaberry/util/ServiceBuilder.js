var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 210, "sl" : 29, "el" : 87, "name" : "ServiceBuilder",
    "methods" : [
              {"sl" : 41, "el" : 43, "sc" : 3},  {"sl" : 49, "el" : 52, "sc" : 3},  {"sl" : 58, "el" : 61, "sc" : 3},  {"sl" : 67, "el" : 70, "sc" : 3},  {"sl" : 76, "el" : 79, "sc" : 3},  {"sl" : 84, "el" : 86, "sc" : 3}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_3" : {
					  "name" : "checkAnnotationTypes",
					  "pass" : true,
					  "methods" : [{"sl": 41 },{"sl": 84 },],
					  "statements" : [{"sl": 42 },{"sl": 85 },]
					  },
		"test_5" : {
					  "name" : "checkNotEquals",
					  "pass" : true,
					  "methods" : [{"sl": 41 },{"sl": 84 },],
					  "statements" : [{"sl": 42 },{"sl": 85 },]
					  },
		"test_11" : {
					  "name" : "serviceFilters",
					  "pass" : true,
					  "methods" : [{"sl": 41 },{"sl": 58 },{"sl": 67 },{"sl": 84 },],
					  "statements" : [{"sl": 42 },{"sl": 59 },{"sl": 60 },{"sl": 68 },{"sl": 69 },{"sl": 85 },]
					  },
		"test_14" : {
					  "name" : "testAnnotationConverter",
					  "pass" : true,
					  "methods" : [{"sl": 41 },{"sl": 49 },{"sl": 84 },],
					  "statements" : [{"sl": 42 },{"sl": 50 },{"sl": 51 },{"sl": 85 },]
					  },
		"test_10" : {
					  "name" : "checkServiceAnnotation",
					  "pass" : true,
					  "methods" : [{"sl": 41 },{"sl": 49 },{"sl": 58 },{"sl": 67 },{"sl": 76 },{"sl": 84 },],
					  "statements" : [{"sl": 42 },{"sl": 50 },{"sl": 51 },{"sl": 59 },{"sl": 60 },{"sl": 68 },{"sl": 69 },{"sl": 77 },{"sl": 78 },{"sl": 85 },]
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
  [ 3 , 14 , 11 , 5 , 10   ] ,
  [ 3 , 14 , 11 , 5 , 10   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 14 , 10   ] ,
  [ 14 , 10   ] ,
  [ 14 , 10   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 11 , 10   ] ,
  [ 11 , 10   ] ,
  [ 11 , 10   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 11 , 10   ] ,
  [ 11 , 10   ] ,
  [ 11 , 10   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 10   ] ,
  [ 10   ] ,
  [ 10   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 3 , 14 , 11 , 5 , 10   ] ,
  [ 3 , 14 , 11 , 5 , 10   ] ,
  [  ] ,
  [  ] 
];
