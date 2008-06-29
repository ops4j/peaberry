var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 34, "sl" : 30, "el" : 72, "name" : "ServiceProxyFactory",
    "methods" : [
              {"sl" : 33, "el" : 33, "sc" : 3},  {"sl" : 35, "el" : 58, "sc" : 3},  {"sl" : 39, "el" : 56, "sc" : 7},  {"sl" : 44, "el" : 46, "sc" : 11},  {"sl" : 48, "el" : 50, "sc" : 11},  {"sl" : 52, "el" : 54, "sc" : 11},  {"sl" : 60, "el" : 71, "sc" : 3},  {"sl" : 64, "el" : 66, "sc" : 7},  {"sl" : 68, "el" : 68, "sc" : 7}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_7" : {
					  "name" : "leasedMultiService",
					  "pass" : false,
					  "methods" : [{"sl": 39 },{"sl": 44 },{"sl": 48 },],
					  "statements" : [{"sl": 40 },{"sl": 45 },{"sl": 49 },]
					  },
		"test_9" : {
					  "name" : "leasedUnaryService",
					  "pass" : false,
					  "methods" : [{"sl": 64 },{"sl": 68 },],
					  "statements" : [{"sl": 65 },]
					  },
		"test_8" : {
					  "name" : "checkInjection",
					  "pass" : true,
					  "methods" : [{"sl": 39 },{"sl": 48 },{"sl": 64 },{"sl": 68 },],
					  "statements" : [{"sl": 40 },{"sl": 49 },{"sl": 65 },]
					  },
		"test_10" : {
					  "name" : "staticMultiService",
					  "pass" : false,
					  "methods" : [{"sl": 39 },{"sl": 44 },{"sl": 48 },],
					  "statements" : [{"sl": 40 },{"sl": 45 },{"sl": 49 },]
					  },
		"test_5" : {
					  "name" : "testWiring",
					  "pass" : true,
					  "methods" : [{"sl": 64 },{"sl": 68 },],
					  "statements" : [{"sl": 65 },]
					  },
		"test_1" : {
					  "name" : "staticUnaryService",
					  "pass" : false,
					  "methods" : [{"sl": 64 },{"sl": 68 },],
					  "statements" : [{"sl": 65 },]
					  },
		"test_6" : {
					  "name" : "checkScoping",
					  "pass" : true,
					  "methods" : [{"sl": 64 },],
					  "statements" : [{"sl": 65 },]
					  },
		"test_2" : {
					  "name" : "checkRanking",
					  "pass" : true,
					  "methods" : [{"sl": 39 },{"sl": 44 },{"sl": 48 },],
					  "statements" : [{"sl": 40 },{"sl": 45 },{"sl": 49 },]
					  },
		"test_4" : {
					  "name" : "unleasedUnaryService",
					  "pass" : true,
					  "methods" : [{"sl": 64 },{"sl": 68 },],
					  "statements" : [{"sl": 65 },]
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
  [ 8 , 7 , 2 , 10   ] ,
  [ 8 , 7 , 2 , 10   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 7 , 2 , 10   ] ,
  [ 7 , 2 , 10   ] ,
  [  ] ,
  [  ] ,
  [ 8 , 7 , 2 , 10   ] ,
  [ 8 , 7 , 2 , 10   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 8 , 1 , 5 , 6 , 9 , 4   ] ,
  [ 8 , 1 , 5 , 6 , 9 , 4   ] ,
  [  ] ,
  [  ] ,
  [ 8 , 1 , 5 , 9 , 4   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] 
];
