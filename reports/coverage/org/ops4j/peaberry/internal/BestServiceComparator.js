var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 175, "sl" : 33, "el" : 64, "name" : "BestServiceComparator",
    "methods" : [
              {"sl" : 38, "el" : 55, "sc" : 3},  {"sl" : 57, "el" : 63, "sc" : 3}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_10" : {
					  "name" : "checkRanking",
					  "pass" : true,
					  "methods" : [{"sl": 38 },{"sl": 57 },],
					  "statements" : [{"sl": 40 },{"sl": 41 },{"sl": 43 },{"sl": 47 },{"sl": 48 },{"sl": 50 },{"sl": 51 },{"sl": 54 },{"sl": 58 },{"sl": 59 },{"sl": 60 },]
					  },
		"test_7" : {
					  "name" : "cornerCases",
					  "pass" : true,
					  "methods" : [{"sl": 38 },{"sl": 57 },],
					  "statements" : [{"sl": 40 },{"sl": 41 },{"sl": 43 },{"sl": 44 },{"sl": 47 },{"sl": 48 },{"sl": 50 },{"sl": 51 },{"sl": 58 },{"sl": 59 },{"sl": 60 },{"sl": 62 },]
					  },
		"test_12" : {
					  "name" : "checkInjection",
					  "pass" : true,
					  "methods" : [{"sl": 38 },{"sl": 57 },],
					  "statements" : [{"sl": 40 },{"sl": 41 },{"sl": 43 },{"sl": 47 },{"sl": 48 },{"sl": 50 },{"sl": 54 },{"sl": 58 },{"sl": 59 },{"sl": 60 },]
					  },
		"test_5" : {
					  "name" : "brokenServices",
					  "pass" : true,
					  "methods" : [{"sl": 38 },{"sl": 57 },],
					  "statements" : [{"sl": 40 },{"sl": 41 },{"sl": 43 },{"sl": 47 },{"sl": 48 },{"sl": 50 },{"sl": 51 },{"sl": 58 },{"sl": 59 },{"sl": 60 },]
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
  [ 5 , 12 , 7 , 10   ] ,
  [  ] ,
  [ 5 , 12 , 7 , 10   ] ,
  [ 5 , 12 , 7 , 10   ] ,
  [  ] ,
  [ 5 , 12 , 7 , 10   ] ,
  [ 7   ] ,
  [  ] ,
  [  ] ,
  [ 5 , 12 , 7 , 10   ] ,
  [ 5 , 12 , 7 , 10   ] ,
  [  ] ,
  [ 5 , 12 , 7 , 10   ] ,
  [ 5 , 7 , 10   ] ,
  [  ] ,
  [  ] ,
  [ 12 , 10   ] ,
  [  ] ,
  [  ] ,
  [ 5 , 12 , 7 , 10   ] ,
  [ 5 , 12 , 7 , 10   ] ,
  [ 5 , 12 , 7 , 10   ] ,
  [ 5 , 12 , 7 , 10   ] ,
  [  ] ,
  [ 7   ] ,
  [  ] ,
  [  ] 
];
