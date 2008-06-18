var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 63, "sl" : 33, "el" : 80, "name" : "ServiceProxyFactory",
    "methods" : [
              {"sl" : 36, "el" : 36, "sc" : 3},  {"sl" : 46, "el" : 61, "sc" : 3},  {"sl" : 50, "el" : 59, "sc" : 7},  {"sl" : 71, "el" : 79, "sc" : 3},  {"sl" : 75, "el" : 77, "sc" : 7}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_3" : {
					  "name" : "unleasedUnaryService",
					  "pass" : true,
					  "methods" : [{"sl": 50 },],
					  "statements" : [{"sl": 52 },{"sl": 54 },{"sl": 55 },{"sl": 57 },]
					  },
		"test_13" : {
					  "name" : "checkRanking",
					  "pass" : true,
					  "methods" : [{"sl": 75 },],
					  "statements" : [{"sl": 76 },]
					  },
		"test_1" : {
					  "name" : "leasedUnaryService",
					  "pass" : true,
					  "methods" : [{"sl": 50 },],
					  "statements" : [{"sl": 52 },{"sl": 54 },{"sl": 55 },{"sl": 57 },]
					  },
		"test_8" : {
					  "name" : "checkInjection",
					  "pass" : true,
					  "methods" : [{"sl": 50 },{"sl": 75 },],
					  "statements" : [{"sl": 52 },{"sl": 54 },{"sl": 55 },{"sl": 57 },{"sl": 76 },]
					  },
		"test_11" : {
					  "name" : "testWiring",
					  "pass" : true,
					  "methods" : [{"sl": 50 },],
					  "statements" : [{"sl": 52 },{"sl": 54 },{"sl": 55 },{"sl": 57 },]
					  },
		"test_0" : {
					  "name" : "staticUnaryService",
					  "pass" : true,
					  "methods" : [{"sl": 50 },],
					  "statements" : [{"sl": 52 },{"sl": 54 },{"sl": 55 },{"sl": 57 },]
					  },
		"test_9" : {
					  "name" : "staticMultiService",
					  "pass" : true,
					  "methods" : [{"sl": 75 },],
					  "statements" : [{"sl": 76 },]
					  },
		"test_2" : {
					  "name" : "checkScoping",
					  "pass" : true,
					  "methods" : [{"sl": 50 },],
					  "statements" : [{"sl": 52 },{"sl": 54 },{"sl": 55 },]
					  },
		"test_7" : {
					  "name" : "leasedMultiService",
					  "pass" : true,
					  "methods" : [{"sl": 75 },],
					  "statements" : [{"sl": 76 },]
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
  [  ] ,
  [ 3 , 8 , 2 , 0 , 11 , 1   ] ,
  [  ] ,
  [ 3 , 8 , 2 , 0 , 11 , 1   ] ,
  [  ] ,
  [ 3 , 8 , 2 , 0 , 11 , 1   ] ,
  [ 3 , 8 , 2 , 0 , 11 , 1   ] ,
  [  ] ,
  [ 3 , 8 , 0 , 11 , 1   ] ,
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
  [ 8 , 7 , 9 , 13   ] ,
  [ 8 , 7 , 9 , 13   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] 
];
