var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 270, "sl" : 31, "el" : 77, "name" : "ServiceAnnotation",
    "methods" : [
              {"sl" : 37, "el" : 40, "sc" : 3},  {"sl" : 42, "el" : 44, "sc" : 3},  {"sl" : 46, "el" : 48, "sc" : 3},  {"sl" : 50, "el" : 52, "sc" : 3},  {"sl" : 54, "el" : 58, "sc" : 3},  {"sl" : 60, "el" : 70, "sc" : 3},  {"sl" : 72, "el" : 76, "sc" : 3}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_15" : {
					  "name" : "testAnnotations",
					  "pass" : true,
					  "methods" : [{"sl": 37 },{"sl": 50 },],
					  "statements" : [{"sl": 38 },{"sl": 39 },{"sl": 51 },]
					  },
		"test_14" : {
					  "name" : "checkServiceAnnotation",
					  "pass" : true,
					  "methods" : [{"sl": 37 },{"sl": 42 },{"sl": 46 },{"sl": 50 },{"sl": 54 },{"sl": 60 },],
					  "statements" : [{"sl": 38 },{"sl": 39 },{"sl": 43 },{"sl": 47 },{"sl": 51 },{"sl": 56 },{"sl": 62 },{"sl": 63 },{"sl": 66 },{"sl": 68 },]
					  },
		"test_6" : {
					  "name" : "serviceFilters",
					  "pass" : true,
					  "methods" : [{"sl": 37 },{"sl": 42 },{"sl": 46 },],
					  "statements" : [{"sl": 38 },{"sl": 39 },{"sl": 43 },{"sl": 47 },]
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
  [ 15 , 6 , 14   ] ,
  [ 15 , 6 , 14   ] ,
  [ 15 , 6 , 14   ] ,
  [  ] ,
  [  ] ,
  [ 6 , 14   ] ,
  [ 6 , 14   ] ,
  [  ] ,
  [  ] ,
  [ 6 , 14   ] ,
  [ 6 , 14   ] ,
  [  ] ,
  [  ] ,
  [ 15 , 14   ] ,
  [ 15 , 14   ] ,
  [  ] ,
  [  ] ,
  [ 14   ] ,
  [  ] ,
  [ 14   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 14   ] ,
  [  ] ,
  [ 14   ] ,
  [ 14   ] ,
  [  ] ,
  [  ] ,
  [ 14   ] ,
  [  ] ,
  [ 14   ] ,
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
