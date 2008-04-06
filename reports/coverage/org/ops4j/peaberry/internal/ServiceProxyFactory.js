var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 182, "sl" : 32, "el" : 78, "name" : "ServiceProxyFactory",
    "methods" : [
              {"sl" : 35, "el" : 35, "sc" : 3},  {"sl" : 45, "el" : 59, "sc" : 3},  {"sl" : 50, "el" : 55, "sc" : 7},  {"sl" : 69, "el" : 77, "sc" : 3},  {"sl" : 73, "el" : 75, "sc" : 7}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_5" : {
					  "name" : "staticUnaryService",
					  "pass" : true ,
					  "methods" : [{"sl": 50 },],
					  "statements" : [{"sl": 54 },]
					  },
		"test_6" : {
					  "name" : "run",
					  "pass" : true ,
					  "methods" : [{"sl": 45 },{"sl": 50 },{"sl": 69 },{"sl": 73 },],
					  "statements" : [{"sl": 48 },{"sl": 49 },{"sl": 54 },{"sl": 58 },{"sl": 72 },{"sl": 74 },]
					  },
		"test_13" : {
					  "name" : "leasedUnaryService",
					  "pass" : true ,
					  "methods" : [{"sl": 50 },],
					  "statements" : [{"sl": 54 },]
					  },
		"test_19" : {
					  "name" : "checkInjection",
					  "pass" : true ,
					  "methods" : [{"sl": 50 },],
					  "statements" : [{"sl": 54 },]
					  },
		"test_18" : {
					  "name" : "testUnaryService",
					  "pass" : true ,
					  "methods" : [{"sl": 50 },],
					  "statements" : [{"sl": 54 },]
					  },
		"test_21" : {
					  "name" : "leasedMultiService",
					  "pass" : true ,
					  "methods" : [{"sl": 73 },],
					  "statements" : [{"sl": 74 },]
					  },
		"test_12" : {
					  "name" : "testMultiService",
					  "pass" : true ,
					  "methods" : [{"sl": 73 },],
					  "statements" : [{"sl": 74 },]
					  },
		"test_10" : {
					  "name" : "unleasedUnaryService",
					  "pass" : true ,
					  "methods" : [{"sl": 50 },],
					  "statements" : [{"sl": 54 },]
					  },
		"test_7" : {
					  "name" : "staticMultiService",
					  "pass" : true ,
					  "methods" : [{"sl": 73 },],
					  "statements" : [{"sl": 74 },]
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
  [ 6   ] ,
  [  ] ,
  [  ] ,
  [ 6   ] ,
  [ 6   ] ,
  [ 10 , 18 , 13 , 19 , 6 , 5   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 10 , 18 , 13 , 19 , 6 , 5   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 6   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 6   ] ,
  [  ] ,
  [  ] ,
  [ 6   ] ,
  [ 7 , 12 , 6 , 21   ] ,
  [ 7 , 12 , 6 , 21   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] 
];
