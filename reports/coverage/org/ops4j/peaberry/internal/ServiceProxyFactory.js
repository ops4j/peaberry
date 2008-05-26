var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 108, "sl" : 34, "el" : 93, "name" : "ServiceProxyFactory",
    "methods" : [
              {"sl" : 37, "el" : 37, "sc" : 3},  {"sl" : 49, "el" : 71, "sc" : 3},  {"sl" : 55, "el" : 62, "sc" : 7},  {"sl" : 83, "el" : 92, "sc" : 3},  {"sl" : 88, "el" : 90, "sc" : 7}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_13" : {
					  "name" : "staticMultiService",
					  "pass" : true,
					  "methods" : [{"sl": 88 },],
					  "statements" : [{"sl": 89 },]
					  },
		"test_11" : {
					  "name" : "testMultiService",
					  "pass" : true,
					  "methods" : [{"sl": 88 },],
					  "statements" : [{"sl": 89 },]
					  },
		"test_4" : {
					  "name" : "staticUnaryService",
					  "pass" : true,
					  "methods" : [{"sl": 55 },],
					  "statements" : [{"sl": 56 },{"sl": 58 },{"sl": 60 },]
					  },
		"test_16" : {
					  "name" : "unleasedUnaryService",
					  "pass" : true,
					  "methods" : [{"sl": 55 },],
					  "statements" : [{"sl": 56 },{"sl": 58 },{"sl": 60 },]
					  },
		"test_15" : {
					  "name" : "testWiring",
					  "pass" : true,
					  "methods" : [{"sl": 55 },],
					  "statements" : [{"sl": 56 },{"sl": 58 },{"sl": 60 },]
					  },
		"test_3" : {
					  "name" : "leasedMultiService",
					  "pass" : true,
					  "methods" : [{"sl": 88 },],
					  "statements" : [{"sl": 89 },]
					  },
		"test_17" : {
					  "name" : "leasedUnaryService",
					  "pass" : true,
					  "methods" : [{"sl": 55 },],
					  "statements" : [{"sl": 56 },{"sl": 58 },{"sl": 60 },]
					  },
		"test_10" : {
					  "name" : "checkInjection",
					  "pass" : true,
					  "methods" : [{"sl": 55 },{"sl": 88 },],
					  "statements" : [{"sl": 56 },{"sl": 58 },{"sl": 60 },{"sl": 89 },]
					  },
		"test_7" : {
					  "name" : "testUnaryService",
					  "pass" : true,
					  "methods" : [{"sl": 55 },],
					  "statements" : [{"sl": 56 },{"sl": 58 },]
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
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 10 , 15 , 16 , 17 , 4 , 7   ] ,
  [ 10 , 15 , 16 , 17 , 4 , 7   ] ,
  [  ] ,
  [ 10 , 15 , 16 , 17 , 4 , 7   ] ,
  [  ] ,
  [ 10 , 15 , 16 , 17 , 4   ] ,
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
  [ 10 , 13 , 3 , 11   ] ,
  [ 10 , 13 , 3 , 11   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] 
];
