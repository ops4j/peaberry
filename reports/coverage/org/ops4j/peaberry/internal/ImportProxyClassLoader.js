var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 96, "sl" : 36, "el" : 87, "name" : "ImportProxyClassLoader",
    "methods" : [
              {"sl" : 42, "el" : 67, "sc" : 3},  {"sl" : 51, "el" : 53, "sc" : 11},  {"sl" : 69, "el" : 71, "sc" : 3},  {"sl" : 73, "el" : 86, "sc" : 3}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_7" : {
					  "name" : "leasedMultiService",
					  "pass" : false,
					  "methods" : [{"sl": 42 },],
					  "statements" : [{"sl": 43 },{"sl": 45 },{"sl": 47 },{"sl": 48 },{"sl": 49 },{"sl": 59 },{"sl": 60 },{"sl": 61 },{"sl": 62 },{"sl": 63 },]
					  },
		"test_8" : {
					  "name" : "checkInjection",
					  "pass" : true,
					  "methods" : [{"sl": 42 },],
					  "statements" : [{"sl": 43 },{"sl": 45 },{"sl": 47 },{"sl": 48 },{"sl": 49 },{"sl": 59 },{"sl": 60 },{"sl": 61 },{"sl": 62 },{"sl": 63 },]
					  },
		"test_10" : {
					  "name" : "staticMultiService",
					  "pass" : false,
					  "methods" : [{"sl": 42 },],
					  "statements" : [{"sl": 43 },{"sl": 45 },{"sl": 47 },{"sl": 48 },{"sl": 49 },{"sl": 59 },{"sl": 60 },{"sl": 61 },{"sl": 62 },{"sl": 63 },]
					  },
		"test_2" : {
					  "name" : "checkRanking",
					  "pass" : true,
					  "methods" : [{"sl": 42 },],
					  "statements" : [{"sl": 43 },{"sl": 45 },{"sl": 47 },{"sl": 48 },{"sl": 49 },{"sl": 59 },{"sl": 60 },{"sl": 61 },{"sl": 62 },{"sl": 63 },]
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
  [ 8 , 7 , 2 , 10   ] ,
  [ 8 , 7 , 2 , 10   ] ,
  [  ] ,
  [ 8 , 7 , 2 , 10   ] ,
  [  ] ,
  [ 8 , 7 , 2 , 10   ] ,
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
  [ 8 , 7 , 2 , 10   ] ,
  [ 8 , 7 , 2 , 10   ] ,
  [ 8 , 7 , 2 , 10   ] ,
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
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] 
];
