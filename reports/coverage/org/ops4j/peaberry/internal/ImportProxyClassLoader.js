var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 143, "sl" : 35, "el" : 89, "name" : "ImportProxyClassLoader",
    "methods" : [
              {"sl" : 38, "el" : 48, "sc" : 3},  {"sl" : 53, "el" : 70, "sc" : 3},  {"sl" : 61, "el" : 63, "sc" : 11},  {"sl" : 72, "el" : 74, "sc" : 3},  {"sl" : 76, "el" : 88, "sc" : 3}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_10" : {
					  "name" : "checkRanking",
					  "pass" : true,
					  "methods" : [{"sl": 38 },{"sl": 53 },],
					  "statements" : [{"sl": 39 },{"sl": 40 },{"sl": 41 },{"sl": 42 },{"sl": 54 },{"sl": 55 },{"sl": 57 },{"sl": 58 },{"sl": 59 },{"sl": 69 },]
					  },
		"test_3" : {
					  "name" : "cornerCases",
					  "pass" : true,
					  "methods" : [{"sl": 38 },{"sl": 53 },{"sl": 61 },{"sl": 72 },{"sl": 76 },],
					  "statements" : [{"sl": 39 },{"sl": 40 },{"sl": 41 },{"sl": 42 },{"sl": 44 },{"sl": 46 },{"sl": 54 },{"sl": 55 },{"sl": 57 },{"sl": 58 },{"sl": 59 },{"sl": 60 },{"sl": 62 },{"sl": 65 },{"sl": 69 },{"sl": 73 },{"sl": 80 },{"sl": 82 },{"sl": 83 },{"sl": 84 },]
					  },
		"test_12" : {
					  "name" : "checkInjection",
					  "pass" : true,
					  "methods" : [{"sl": 38 },{"sl": 53 },{"sl": 76 },],
					  "statements" : [{"sl": 39 },{"sl": 40 },{"sl": 41 },{"sl": 42 },{"sl": 54 },{"sl": 55 },{"sl": 57 },{"sl": 58 },{"sl": 59 },{"sl": 69 },{"sl": 80 },{"sl": 82 },{"sl": 87 },]
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
  [ 12 , 3 , 10   ] ,
  [ 12 , 3 , 10   ] ,
  [ 12 , 3 , 10   ] ,
  [ 12 , 3 , 10   ] ,
  [ 12 , 3 , 10   ] ,
  [  ] ,
  [ 3   ] ,
  [  ] ,
  [ 3   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 12 , 3 , 10   ] ,
  [ 12 , 3 , 10   ] ,
  [ 12 , 3 , 10   ] ,
  [  ] ,
  [ 12 , 3 , 10   ] ,
  [ 12 , 3 , 10   ] ,
  [ 12 , 3 , 10   ] ,
  [ 3   ] ,
  [ 3   ] ,
  [ 3   ] ,
  [  ] ,
  [  ] ,
  [ 3   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 12 , 3 , 10   ] ,
  [  ] ,
  [  ] ,
  [ 3   ] ,
  [ 3   ] ,
  [  ] ,
  [  ] ,
  [ 12 , 3   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 12 , 3   ] ,
  [  ] ,
  [ 12 , 3   ] ,
  [ 3   ] ,
  [ 3   ] ,
  [  ] ,
  [  ] ,
  [ 12   ] ,
  [  ] ,
  [  ] 
];
