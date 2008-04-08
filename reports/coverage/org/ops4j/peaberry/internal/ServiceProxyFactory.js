var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 198, "sl" : 33, "el" : 86, "name" : "ServiceProxyFactory",
    "methods" : [
              {"sl" : 36, "el" : 36, "sc" : 3},  {"sl" : 47, "el" : 66, "sc" : 3},  {"sl" : 52, "el" : 57, "sc" : 7},  {"sl" : 77, "el" : 85, "sc" : 3},  {"sl" : 81, "el" : 83, "sc" : 7}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_15" : {
					  "name" : "testUnaryService",
					  "pass" : true ,
					  "methods" : [{"sl": 52 },],
					  "statements" : [{"sl": 56 },]
					  },
		"test_12" : {
					  "name" : "unleasedUnaryService",
					  "pass" : true ,
					  "methods" : [{"sl": 52 },],
					  "statements" : [{"sl": 56 },]
					  },
		"test_20" : {
					  "name" : "staticMultiService",
					  "pass" : true ,
					  "methods" : [{"sl": 81 },],
					  "statements" : [{"sl": 82 },]
					  },
		"test_17" : {
					  "name" : "staticUnaryService",
					  "pass" : true ,
					  "methods" : [{"sl": 52 },],
					  "statements" : [{"sl": 56 },]
					  },
		"test_22" : {
					  "name" : "checkInjection",
					  "pass" : true ,
					  "methods" : [{"sl": 52 },{"sl": 81 },],
					  "statements" : [{"sl": 56 },{"sl": 82 },]
					  },
		"test_16" : {
					  "name" : "leasedUnaryService",
					  "pass" : true ,
					  "methods" : [{"sl": 52 },],
					  "statements" : [{"sl": 56 },]
					  },
		"test_14" : {
					  "name" : "testMultiService",
					  "pass" : true ,
					  "methods" : [{"sl": 81 },],
					  "statements" : [{"sl": 82 },]
					  },
		"test_6" : {
					  "name" : "leasedMultiService",
					  "pass" : true ,
					  "methods" : [{"sl": 81 },],
					  "statements" : [{"sl": 82 },]
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
  [ 17 , 12 , 15 , 16 , 22   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 17 , 12 , 15 , 16 , 22   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 20 , 14 , 6 , 22   ] ,
  [ 20 , 14 , 6 , 22   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] 
];
