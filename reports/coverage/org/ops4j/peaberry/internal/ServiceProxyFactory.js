var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 50, "sl" : 31, "el" : 95, "name" : "ServiceProxyFactory",
    "methods" : [
              {"sl" : 34, "el" : 34, "sc" : 3},  {"sl" : 36, "el" : 59, "sc" : 3},  {"sl" : 40, "el" : 57, "sc" : 7},  {"sl" : 45, "el" : 47, "sc" : 11},  {"sl" : 49, "el" : 51, "sc" : 11},  {"sl" : 53, "el" : 55, "sc" : 11},  {"sl" : 61, "el" : 90, "sc" : 3},  {"sl" : 68, "el" : 74, "sc" : 7},  {"sl" : 76, "el" : 86, "sc" : 7},  {"sl" : 92, "el" : 94, "sc" : 3}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_1" : {
					  "name" : "testWiring",
					  "pass" : true,
					  "methods" : [{"sl": 68 },{"sl": 76 },],
					  "statements" : [{"sl": 69 },{"sl": 70 },{"sl": 71 },{"sl": 73 },{"sl": 77 },{"sl": 78 },{"sl": 79 },{"sl": 82 },{"sl": 83 },]
					  },
		"test_2" : {
					  "name" : "checkScoping",
					  "pass" : true,
					  "methods" : [{"sl": 68 },{"sl": 76 },],
					  "statements" : [{"sl": 69 },{"sl": 70 },{"sl": 71 },{"sl": 73 },{"sl": 77 },{"sl": 78 },{"sl": 79 },{"sl": 82 },{"sl": 83 },]
					  },
		"test_10" : {
					  "name" : "run",
					  "pass" : true,
					  "methods" : [{"sl": 68 },{"sl": 76 },],
					  "statements" : [{"sl": 69 },{"sl": 70 },{"sl": 73 },{"sl": 77 },{"sl": 78 },{"sl": 79 },{"sl": 82 },{"sl": 83 },]
					  },
		"test_12" : {
					  "name" : "checkRanking",
					  "pass" : true,
					  "methods" : [{"sl": 40 },{"sl": 45 },{"sl": 49 },{"sl": 53 },{"sl": 92 },],
					  "statements" : [{"sl": 41 },{"sl": 46 },{"sl": 50 },{"sl": 54 },{"sl": 93 },]
					  },
		"test_4" : {
					  "name" : "checkInjection",
					  "pass" : true,
					  "methods" : [{"sl": 40 },{"sl": 45 },{"sl": 49 },{"sl": 68 },{"sl": 76 },{"sl": 92 },],
					  "statements" : [{"sl": 41 },{"sl": 46 },{"sl": 50 },{"sl": 69 },{"sl": 70 },{"sl": 71 },{"sl": 73 },{"sl": 77 },{"sl": 78 },{"sl": 79 },{"sl": 82 },{"sl": 83 },{"sl": 93 },]
					  },
		"test_9" : {
					  "name" : "stickyService",
					  "pass" : true,
					  "methods" : [{"sl": 68 },],
					  "statements" : [{"sl": 69 },{"sl": 70 },{"sl": 71 },{"sl": 73 },]
					  },
		"test_0" : {
					  "name" : "testContention",
					  "pass" : true,
					  "methods" : [{"sl": 68 },{"sl": 76 },],
					  "statements" : [{"sl": 69 },{"sl": 70 },{"sl": 71 },{"sl": 73 },{"sl": 77 },{"sl": 78 },{"sl": 79 },{"sl": 82 },{"sl": 83 },]
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
  [ 12 , 4   ] ,
  [ 12 , 4   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 12 , 4   ] ,
  [ 12 , 4   ] ,
  [  ] ,
  [  ] ,
  [ 12 , 4   ] ,
  [ 12 , 4   ] ,
  [  ] ,
  [  ] ,
  [ 12   ] ,
  [ 12   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 2 , 4 , 9 , 1 , 10 , 0   ] ,
  [ 2 , 4 , 9 , 1 , 10 , 0   ] ,
  [ 2 , 4 , 9 , 1 , 10 , 0   ] ,
  [ 2 , 4 , 9 , 1 , 0   ] ,
  [  ] ,
  [ 2 , 4 , 9 , 1 , 10 , 0   ] ,
  [  ] ,
  [  ] ,
  [ 2 , 4 , 1 , 10 , 0   ] ,
  [ 2 , 4 , 1 , 10 , 0   ] ,
  [ 2 , 4 , 1 , 10 , 0   ] ,
  [ 2 , 4 , 1 , 10 , 0   ] ,
  [  ] ,
  [  ] ,
  [ 2 , 4 , 1 , 10 , 0   ] ,
  [ 2 , 4 , 1 , 10 , 0   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 12 , 4   ] ,
  [ 12 , 4   ] ,
  [  ] ,
  [  ] 
];
