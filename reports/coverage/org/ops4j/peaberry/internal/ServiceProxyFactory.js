var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 50, "sl" : 31, "el" : 89, "name" : "ServiceProxyFactory",
    "methods" : [
              {"sl" : 34, "el" : 34, "sc" : 3},  {"sl" : 36, "el" : 59, "sc" : 3},  {"sl" : 40, "el" : 57, "sc" : 7},  {"sl" : 45, "el" : 47, "sc" : 11},  {"sl" : 49, "el" : 51, "sc" : 11},  {"sl" : 53, "el" : 55, "sc" : 11},  {"sl" : 61, "el" : 88, "sc" : 3},  {"sl" : 68, "el" : 74, "sc" : 7},  {"sl" : 76, "el" : 84, "sc" : 7}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_5" : {
					  "name" : "testWiring",
					  "pass" : true,
					  "methods" : [{"sl": 68 },{"sl": 76 },],
					  "statements" : [{"sl": 69 },{"sl": 70 },{"sl": 71 },{"sl": 73 },{"sl": 77 },{"sl": 78 },{"sl": 80 },{"sl": 81 },]
					  },
		"test_3" : {
					  "name" : "checkScoping",
					  "pass" : true,
					  "methods" : [{"sl": 68 },{"sl": 76 },],
					  "statements" : [{"sl": 69 },{"sl": 70 },{"sl": 71 },{"sl": 73 },{"sl": 77 },{"sl": 78 },{"sl": 80 },{"sl": 81 },]
					  },
		"test_6" : {
					  "name" : "checkRanking",
					  "pass" : true,
					  "methods" : [{"sl": 40 },{"sl": 45 },{"sl": 49 },{"sl": 53 },],
					  "statements" : [{"sl": 41 },{"sl": 46 },{"sl": 50 },{"sl": 54 },]
					  },
		"test_7" : {
					  "name" : "checkInjection",
					  "pass" : true,
					  "methods" : [{"sl": 40 },{"sl": 45 },{"sl": 49 },{"sl": 68 },{"sl": 76 },],
					  "statements" : [{"sl": 41 },{"sl": 46 },{"sl": 50 },{"sl": 69 },{"sl": 70 },{"sl": 71 },{"sl": 73 },{"sl": 77 },{"sl": 78 },{"sl": 80 },{"sl": 81 },]
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
  [ 7 , 6   ] ,
  [ 7 , 6   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 7 , 6   ] ,
  [ 7 , 6   ] ,
  [  ] ,
  [  ] ,
  [ 7 , 6   ] ,
  [ 7 , 6   ] ,
  [  ] ,
  [  ] ,
  [ 6   ] ,
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
  [  ] ,
  [  ] ,
  [  ] ,
  [ 7 , 3 , 5   ] ,
  [ 7 , 3 , 5   ] ,
  [ 7 , 3 , 5   ] ,
  [ 7 , 3 , 5   ] ,
  [  ] ,
  [ 7 , 3 , 5   ] ,
  [  ] ,
  [  ] ,
  [ 7 , 3 , 5   ] ,
  [ 7 , 3 , 5   ] ,
  [ 7 , 3 , 5   ] ,
  [  ] ,
  [ 7 , 3 , 5   ] ,
  [ 7 , 3 , 5   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] 
];
