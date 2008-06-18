var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 75, "sl" : 38, "el" : 115, "name" : "Attributes",
    "methods" : [
              {"sl" : 41, "el" : 41, "sc" : 3},  {"sl" : 49, "el" : 75, "sc" : 3},  {"sl" : 83, "el" : 98, "sc" : 3},  {"sl" : 106, "el" : 114, "sc" : 3}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_3" : {
					  "name" : "unleasedUnaryService",
					  "pass" : true,
					  "methods" : [{"sl": 49 },],
					  "statements" : [{"sl": 51 },{"sl": 59 },{"sl": 60 },{"sl": 61 },{"sl": 62 },{"sl": 67 },{"sl": 68 },{"sl": 69 },{"sl": 70 },{"sl": 74 },]
					  },
		"test_13" : {
					  "name" : "checkRanking",
					  "pass" : true,
					  "methods" : [{"sl": 49 },],
					  "statements" : [{"sl": 51 },{"sl": 59 },{"sl": 60 },{"sl": 61 },{"sl": 62 },{"sl": 67 },{"sl": 68 },{"sl": 69 },{"sl": 70 },{"sl": 74 },]
					  },
		"test_1" : {
					  "name" : "leasedUnaryService",
					  "pass" : true,
					  "methods" : [{"sl": 49 },],
					  "statements" : [{"sl": 51 },{"sl": 59 },{"sl": 60 },{"sl": 61 },{"sl": 62 },{"sl": 67 },{"sl": 68 },{"sl": 69 },{"sl": 70 },{"sl": 74 },]
					  },
		"test_8" : {
					  "name" : "checkInjection",
					  "pass" : true,
					  "methods" : [{"sl": 49 },{"sl": 106 },],
					  "statements" : [{"sl": 51 },{"sl": 59 },{"sl": 60 },{"sl": 61 },{"sl": 62 },{"sl": 67 },{"sl": 68 },{"sl": 69 },{"sl": 70 },{"sl": 74 },{"sl": 107 },{"sl": 109 },{"sl": 110 },{"sl": 113 },]
					  },
		"test_0" : {
					  "name" : "staticUnaryService",
					  "pass" : true,
					  "methods" : [{"sl": 49 },],
					  "statements" : [{"sl": 51 },{"sl": 59 },{"sl": 60 },{"sl": 61 },{"sl": 62 },{"sl": 67 },{"sl": 68 },{"sl": 69 },{"sl": 70 },{"sl": 74 },]
					  },
		"test_10" : {
					  "name" : "testNameConverter",
					  "pass" : true,
					  "methods" : [{"sl": 83 },],
					  "statements" : [{"sl": 85 },{"sl": 86 },{"sl": 88 },{"sl": 89 },{"sl": 90 },{"sl": 91 },{"sl": 93 },{"sl": 97 },]
					  },
		"test_9" : {
					  "name" : "staticMultiService",
					  "pass" : true,
					  "methods" : [{"sl": 49 },],
					  "statements" : [{"sl": 51 },{"sl": 59 },{"sl": 60 },{"sl": 61 },{"sl": 62 },{"sl": 67 },{"sl": 68 },{"sl": 69 },{"sl": 70 },{"sl": 74 },]
					  },
		"test_7" : {
					  "name" : "leasedMultiService",
					  "pass" : true,
					  "methods" : [{"sl": 49 },],
					  "statements" : [{"sl": 51 },{"sl": 59 },{"sl": 60 },{"sl": 61 },{"sl": 62 },{"sl": 67 },{"sl": 68 },{"sl": 69 },{"sl": 70 },{"sl": 74 },]
					  },
		"test_12" : {
					  "name" : "testObjectClassConverter",
					  "pass" : true,
					  "methods" : [{"sl": 106 },],
					  "statements" : [{"sl": 107 },{"sl": 109 },{"sl": 110 },{"sl": 113 },]
					  },
		"test_5" : {
					  "name" : "testPropertyConverter",
					  "pass" : true,
					  "methods" : [{"sl": 49 },],
					  "statements" : [{"sl": 51 },{"sl": 59 },{"sl": 60 },{"sl": 67 },{"sl": 68 },{"sl": 69 },{"sl": 70 },{"sl": 74 },]
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
  [ 3 , 8 , 5 , 9 , 0 , 7 , 13 , 1   ] ,
  [  ] ,
  [ 3 , 8 , 5 , 9 , 0 , 7 , 13 , 1   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 3 , 8 , 5 , 9 , 0 , 7 , 13 , 1   ] ,
  [ 3 , 8 , 5 , 9 , 0 , 7 , 13 , 1   ] ,
  [ 3 , 8 , 9 , 0 , 7 , 13 , 1   ] ,
  [ 3 , 8 , 9 , 0 , 7 , 13 , 1   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 3 , 8 , 5 , 9 , 0 , 7 , 13 , 1   ] ,
  [ 3 , 8 , 5 , 9 , 0 , 7 , 13 , 1   ] ,
  [ 3 , 8 , 5 , 9 , 0 , 7 , 13 , 1   ] ,
  [ 3 , 8 , 5 , 9 , 0 , 7 , 13 , 1   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 3 , 8 , 5 , 9 , 0 , 7 , 13 , 1   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 10   ] ,
  [  ] ,
  [ 10   ] ,
  [ 10   ] ,
  [  ] ,
  [ 10   ] ,
  [ 10   ] ,
  [ 10   ] ,
  [ 10   ] ,
  [  ] ,
  [ 10   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 10   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 8 , 12   ] ,
  [ 8 , 12   ] ,
  [  ] ,
  [ 8 , 12   ] ,
  [ 8 , 12   ] ,
  [  ] ,
  [  ] ,
  [ 8 , 12   ] ,
  [  ] ,
  [  ] 
];
