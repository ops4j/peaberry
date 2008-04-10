var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 223, "sl" : 34, "el" : 91, "name" : "ServiceProxyFactory",
    "methods" : [
              {"sl" : 37, "el" : 37, "sc" : 3},  {"sl" : 48, "el" : 71, "sc" : 3},  {"sl" : 53, "el" : 62, "sc" : 7},  {"sl" : 82, "el" : 90, "sc" : 3},  {"sl" : 86, "el" : 88, "sc" : 7}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_19" : {
					  "name" : "testUnaryService",
					  "pass" : true ,
					  "methods" : [{"sl": 53 },],
					  "statements" : [{"sl": 56 },{"sl": 58 },]
					  },
		"test_7" : {
					  "name" : "leasedMultiService",
					  "pass" : true ,
					  "methods" : [{"sl": 86 },],
					  "statements" : [{"sl": 87 },]
					  },
		"test_14" : {
					  "name" : "staticMultiService",
					  "pass" : true ,
					  "methods" : [{"sl": 86 },],
					  "statements" : [{"sl": 87 },]
					  },
		"test_15" : {
					  "name" : "unleasedUnaryService",
					  "pass" : true ,
					  "methods" : [{"sl": 53 },],
					  "statements" : [{"sl": 56 },{"sl": 58 },{"sl": 60 },]
					  },
		"test_20" : {
					  "name" : "checkInjection",
					  "pass" : true ,
					  "methods" : [{"sl": 53 },{"sl": 86 },],
					  "statements" : [{"sl": 56 },{"sl": 58 },{"sl": 60 },{"sl": 87 },]
					  },
		"test_4" : {
					  "name" : "staticUnaryService",
					  "pass" : true ,
					  "methods" : [{"sl": 53 },],
					  "statements" : [{"sl": 56 },{"sl": 58 },{"sl": 60 },]
					  },
		"test_12" : {
					  "name" : "testMultiService",
					  "pass" : true ,
					  "methods" : [{"sl": 86 },],
					  "statements" : [{"sl": 87 },]
					  },
		"test_5" : {
					  "name" : "leasedUnaryService",
					  "pass" : true ,
					  "methods" : [{"sl": 53 },],
					  "statements" : [{"sl": 56 },{"sl": 58 },{"sl": 60 },]
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
  [ 15 , 19 , 5 , 4 , 20   ] ,
  [  ] ,
  [  ] ,
  [ 15 , 19 , 5 , 4 , 20   ] ,
  [  ] ,
  [ 15 , 19 , 5 , 4 , 20   ] ,
  [  ] ,
  [ 15 , 5 , 4 , 20   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 12 , 14 , 20 , 7   ] ,
  [ 12 , 14 , 20 , 7   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] 
];
