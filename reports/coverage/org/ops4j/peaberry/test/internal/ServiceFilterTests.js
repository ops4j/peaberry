var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 402, "sl" : 38, "el" : 96, "name" : "ServiceFilterTests",
    "methods" : [
             {"sl" : 40, "el" : 43, "sc" : 3},  {"sl" : 45, "el" : 50, "sc" : 3},  {"sl" : 52, "el" : 54, "sc" : 3},  {"sl" : 56, "el" : 58, "sc" : 3},  {"sl" : 60, "el" : 68, "sc" : 3},  {"sl" : 76, "el" : 79, "sc" : 3},  {"sl" : 81, "el" : 83, "sc" : 3},  {"sl" : 85, "el" : 95, "sc" : 3}  ]}
    ,
    {"id" : 424, "sl" : 70, "el" : 70, "name" : "ServiceFilterTests.A",
    "methods" : [
             ]}
    ,
    {"id" : 424, "sl" : 72, "el" : 72, "name" : "ServiceFilterTests.B",
    "methods" : [
             ]}
    ,
    {"id" : 424, "sl" : 74, "el" : 74, "name" : "ServiceFilterTests.C",
    "methods" : [
              ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_6" : {
					  "name" : "run",
					  "pass" : true ,
					  "methods" : [{"sl": 40 },{"sl": 45 },{"sl": 52 },{"sl": 56 },{"sl": 60 },{"sl": 76 },{"sl": 81 },{"sl": 85 },],
					  "statements" : [{"sl": 41 },{"sl": 42 },{"sl": 46 },{"sl": 47 },{"sl": 48 },{"sl": 49 },{"sl": 53 },{"sl": 57 },{"sl": 61 },{"sl": 62 },{"sl": 63 },{"sl": 65 },{"sl": 66 },{"sl": 67 },{"sl": 77 },{"sl": 78 },{"sl": 82 },{"sl": 86 },{"sl": 88 },{"sl": 91 },{"sl": 94 },]
					  },
		"test_4" : {
					  "name" : "serviceTypes",
					  "pass" : true ,
					  "methods" : [{"sl": 40 },{"sl": 45 },],
					  "statements" : [{"sl": 41 },{"sl": 42 },{"sl": 46 },{"sl": 47 },{"sl": 48 },{"sl": 49 },]
					  },
		"test_17" : {
					  "name" : "serviceFilters",
					  "pass" : true ,
					  "methods" : [{"sl": 76 },{"sl": 81 },{"sl": 85 },],
					  "statements" : [{"sl": 77 },{"sl": 78 },{"sl": 82 },{"sl": 86 },{"sl": 88 },{"sl": 91 },{"sl": 94 },]
					  },
		"test_14" : {
					  "name" : "sequenceCheck",
					  "pass" : true ,
					  "methods" : [{"sl": 52 },{"sl": 56 },{"sl": 60 },],
					  "statements" : [{"sl": 53 },{"sl": 57 },{"sl": 61 },{"sl": 62 },{"sl": 63 },{"sl": 65 },{"sl": 66 },{"sl": 67 },]
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
  [ 6 , 4   ] ,
  [ 6 , 4   ] ,
  [ 6 , 4   ] ,
  [  ] ,
  [  ] ,
  [ 6 , 4   ] ,
  [ 6 , 4   ] ,
  [ 6 , 4   ] ,
  [ 6 , 4   ] ,
  [ 6 , 4   ] ,
  [  ] ,
  [  ] ,
  [ 14 , 6   ] ,
  [ 14 , 6   ] ,
  [  ] ,
  [  ] ,
  [ 14 , 6   ] ,
  [ 14 , 6   ] ,
  [  ] ,
  [  ] ,
  [ 14 , 6   ] ,
  [ 14 , 6   ] ,
  [ 14 , 6   ] ,
  [ 14 , 6   ] ,
  [  ] ,
  [ 14 , 6   ] ,
  [ 14 , 6   ] ,
  [ 14 , 6   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 17 , 6   ] ,
  [ 17 , 6   ] ,
  [ 17 , 6   ] ,
  [  ] ,
  [  ] ,
  [ 17 , 6   ] ,
  [ 17 , 6   ] ,
  [  ] ,
  [  ] ,
  [ 17 , 6   ] ,
  [ 17 , 6   ] ,
  [  ] ,
  [ 17 , 6   ] ,
  [  ] ,
  [  ] ,
  [ 17 , 6   ] ,
  [  ] ,
  [  ] ,
  [ 17 , 6   ] ,
  [  ] ,
  [  ] 
];
