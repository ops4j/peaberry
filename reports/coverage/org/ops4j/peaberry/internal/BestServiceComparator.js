var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 41, "sl" : 33, "el" : 67, "name" : "BestServiceComparator",
    "methods" : [
              {"sl" : 38, "el" : 57, "sc" : 3},  {"sl" : 60, "el" : 66, "sc" : 3}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_1" : {
					  "name" : "checkInjection",
					  "pass" : true,
					  "methods" : [{"sl": 38 },{"sl": 60 },],
					  "statements" : [{"sl": 40 },{"sl": 41 },{"sl": 43 },{"sl": 47 },{"sl": 48 },{"sl": 50 },{"sl": 56 },{"sl": 61 },{"sl": 62 },{"sl": 63 },]
					  },
		"test_14" : {
					  "name" : "checkRanking",
					  "pass" : true,
					  "methods" : [{"sl": 38 },{"sl": 60 },],
					  "statements" : [{"sl": 40 },{"sl": 41 },{"sl": 43 },{"sl": 47 },{"sl": 48 },{"sl": 50 },{"sl": 52 },{"sl": 56 },{"sl": 61 },{"sl": 62 },{"sl": 63 },]
					  },
		"test_4" : {
					  "name" : "cornerCases",
					  "pass" : true,
					  "methods" : [{"sl": 38 },{"sl": 60 },],
					  "statements" : [{"sl": 40 },{"sl": 41 },{"sl": 43 },{"sl": 44 },{"sl": 47 },{"sl": 48 },{"sl": 50 },{"sl": 52 },{"sl": 61 },{"sl": 62 },{"sl": 63 },{"sl": 65 },]
					  },
		"test_12" : {
					  "name" : "brokenServices",
					  "pass" : true,
					  "methods" : [{"sl": 38 },{"sl": 60 },],
					  "statements" : [{"sl": 40 },{"sl": 41 },{"sl": 43 },{"sl": 47 },{"sl": 48 },{"sl": 50 },{"sl": 52 },{"sl": 61 },{"sl": 62 },{"sl": 63 },]
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
  [ 14 , 12 , 1 , 4   ] ,
  [  ] ,
  [ 14 , 12 , 1 , 4   ] ,
  [ 14 , 12 , 1 , 4   ] ,
  [  ] ,
  [ 14 , 12 , 1 , 4   ] ,
  [ 4   ] ,
  [  ] ,
  [  ] ,
  [ 14 , 12 , 1 , 4   ] ,
  [ 14 , 12 , 1 , 4   ] ,
  [  ] ,
  [ 14 , 12 , 1 , 4   ] ,
  [  ] ,
  [ 14 , 12 , 4   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 14 , 1   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 14 , 12 , 1 , 4   ] ,
  [ 14 , 12 , 1 , 4   ] ,
  [ 14 , 12 , 1 , 4   ] ,
  [ 14 , 12 , 1 , 4   ] ,
  [  ] ,
  [ 4   ] ,
  [  ] ,
  [  ] 
];
