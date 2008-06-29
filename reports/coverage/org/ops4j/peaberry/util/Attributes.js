var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 50, "sl" : 38, "el" : 115, "name" : "Attributes",
    "methods" : [
              {"sl" : 41, "el" : 41, "sc" : 3},  {"sl" : 49, "el" : 75, "sc" : 3},  {"sl" : 83, "el" : 98, "sc" : 3},  {"sl" : 106, "el" : 114, "sc" : 3}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_7" : {
					  "name" : "leasedMultiService",
					  "pass" : false,
					  "methods" : [{"sl": 49 },],
					  "statements" : [{"sl": 51 },{"sl": 59 },{"sl": 60 },{"sl": 61 },{"sl": 62 },{"sl": 67 },{"sl": 68 },{"sl": 69 },{"sl": 70 },{"sl": 74 },]
					  },
		"test_9" : {
					  "name" : "leasedUnaryService",
					  "pass" : false,
					  "methods" : [{"sl": 49 },],
					  "statements" : [{"sl": 51 },{"sl": 59 },{"sl": 60 },{"sl": 61 },{"sl": 62 },{"sl": 67 },{"sl": 68 },{"sl": 69 },{"sl": 70 },{"sl": 74 },]
					  },
		"test_11" : {
					  "name" : "testPropertyConverter",
					  "pass" : true,
					  "methods" : [{"sl": 49 },],
					  "statements" : [{"sl": 51 },{"sl": 59 },{"sl": 60 },{"sl": 67 },{"sl": 68 },{"sl": 69 },{"sl": 70 },{"sl": 74 },]
					  },
		"test_8" : {
					  "name" : "checkInjection",
					  "pass" : true,
					  "methods" : [{"sl": 49 },{"sl": 106 },],
					  "statements" : [{"sl": 51 },{"sl": 59 },{"sl": 60 },{"sl": 61 },{"sl": 62 },{"sl": 67 },{"sl": 68 },{"sl": 69 },{"sl": 70 },{"sl": 74 },{"sl": 107 },{"sl": 109 },{"sl": 110 },{"sl": 113 },]
					  },
		"test_10" : {
					  "name" : "staticMultiService",
					  "pass" : false,
					  "methods" : [{"sl": 49 },],
					  "statements" : [{"sl": 51 },{"sl": 59 },{"sl": 60 },{"sl": 61 },{"sl": 62 },{"sl": 67 },{"sl": 68 },{"sl": 69 },{"sl": 70 },{"sl": 74 },]
					  },
		"test_1" : {
					  "name" : "staticUnaryService",
					  "pass" : false,
					  "methods" : [{"sl": 49 },],
					  "statements" : [{"sl": 51 },{"sl": 59 },{"sl": 60 },{"sl": 61 },{"sl": 62 },{"sl": 67 },{"sl": 68 },{"sl": 69 },{"sl": 70 },{"sl": 74 },]
					  },
		"test_13" : {
					  "name" : "testNameConverter",
					  "pass" : true,
					  "methods" : [{"sl": 83 },],
					  "statements" : [{"sl": 85 },{"sl": 86 },{"sl": 88 },{"sl": 89 },{"sl": 90 },{"sl": 91 },{"sl": 93 },{"sl": 97 },]
					  },
		"test_2" : {
					  "name" : "checkRanking",
					  "pass" : true,
					  "methods" : [{"sl": 49 },],
					  "statements" : [{"sl": 51 },{"sl": 59 },{"sl": 60 },{"sl": 61 },{"sl": 62 },{"sl": 67 },{"sl": 68 },{"sl": 69 },{"sl": 70 },{"sl": 74 },]
					  },
		"test_0" : {
					  "name" : "testObjectClassConverter",
					  "pass" : true,
					  "methods" : [{"sl": 106 },],
					  "statements" : [{"sl": 107 },{"sl": 109 },{"sl": 110 },{"sl": 113 },]
					  },
		"test_4" : {
					  "name" : "unleasedUnaryService",
					  "pass" : true,
					  "methods" : [{"sl": 49 },],
					  "statements" : [{"sl": 51 },{"sl": 59 },{"sl": 60 },{"sl": 61 },{"sl": 62 },{"sl": 67 },{"sl": 68 },{"sl": 69 },{"sl": 70 },{"sl": 74 },]
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
  [ 8 , 11 , 7 , 1 , 2 , 9 , 4 , 10   ] ,
  [  ] ,
  [ 8 , 11 , 7 , 1 , 2 , 9 , 4 , 10   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 8 , 11 , 7 , 1 , 2 , 9 , 4 , 10   ] ,
  [ 8 , 11 , 7 , 1 , 2 , 9 , 4 , 10   ] ,
  [ 8 , 7 , 1 , 2 , 9 , 4 , 10   ] ,
  [ 8 , 7 , 1 , 2 , 9 , 4 , 10   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 8 , 11 , 7 , 1 , 2 , 9 , 4 , 10   ] ,
  [ 8 , 11 , 7 , 1 , 2 , 9 , 4 , 10   ] ,
  [ 8 , 11 , 7 , 1 , 2 , 9 , 4 , 10   ] ,
  [ 8 , 11 , 7 , 1 , 2 , 9 , 4 , 10   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 8 , 11 , 7 , 1 , 2 , 9 , 4 , 10   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 13   ] ,
  [  ] ,
  [ 13   ] ,
  [ 13   ] ,
  [  ] ,
  [ 13   ] ,
  [ 13   ] ,
  [ 13   ] ,
  [ 13   ] ,
  [  ] ,
  [ 13   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 13   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 0 , 8   ] ,
  [ 0 , 8   ] ,
  [  ] ,
  [ 0 , 8   ] ,
  [ 0 , 8   ] ,
  [  ] ,
  [  ] ,
  [ 0 , 8   ] ,
  [  ] ,
  [  ] 
];
