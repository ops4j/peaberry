var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 97, "sl" : 34, "el" : 91, "name" : "ServiceProxyFactory",
    "methods" : [
              {"sl" : 37, "el" : 37, "sc" : 3},  {"sl" : 49, "el" : 70, "sc" : 3},  {"sl" : 54, "el" : 61, "sc" : 7},  {"sl" : 82, "el" : 90, "sc" : 3},  {"sl" : 86, "el" : 88, "sc" : 7}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_1" : {
					  "name" : "checkInjection",
					  "pass" : true ,
					  "methods" : [{"sl": 54 },{"sl": 86 },],
					  "statements" : [{"sl": 55 },{"sl": 57 },{"sl": 59 },{"sl": 87 },]
					  },
		"test_13" : {
					  "name" : "testUnaryService",
					  "pass" : true ,
					  "methods" : [{"sl": 54 },],
					  "statements" : [{"sl": 55 },{"sl": 57 },]
					  },
		"test_12" : {
					  "name" : "testMultiService",
					  "pass" : true ,
					  "methods" : [{"sl": 86 },],
					  "statements" : [{"sl": 87 },]
					  },
		"test_6" : {
					  "name" : "staticUnaryService",
					  "pass" : true ,
					  "methods" : [{"sl": 54 },],
					  "statements" : [{"sl": 55 },{"sl": 57 },{"sl": 59 },]
					  },
		"test_7" : {
					  "name" : "leasedUnaryService",
					  "pass" : true ,
					  "methods" : [{"sl": 54 },],
					  "statements" : [{"sl": 55 },{"sl": 57 },{"sl": 59 },]
					  },
		"test_16" : {
					  "name" : "leasedMultiService",
					  "pass" : true ,
					  "methods" : [{"sl": 86 },],
					  "statements" : [{"sl": 87 },]
					  },
		"test_21" : {
					  "name" : "staticMultiService",
					  "pass" : true ,
					  "methods" : [{"sl": 86 },],
					  "statements" : [{"sl": 87 },]
					  },
		"test_20" : {
					  "name" : "unleasedUnaryService",
					  "pass" : true ,
					  "methods" : [{"sl": 54 },],
					  "statements" : [{"sl": 55 },{"sl": 57 },{"sl": 59 },]
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
  [ 6 , 7 , 1 , 20 , 13   ] ,
  [ 6 , 7 , 1 , 20 , 13   ] ,
  [  ] ,
  [ 6 , 7 , 1 , 20 , 13   ] ,
  [  ] ,
  [ 6 , 7 , 1 , 20   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 16 , 12 , 1 , 21   ] ,
  [ 16 , 12 , 1 , 21   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] 
];
