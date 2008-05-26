var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 125, "sl" : 36, "el" : 94, "name" : "Attributes",
    "methods" : [
              {"sl" : 39, "el" : 39, "sc" : 3},  {"sl" : 47, "el" : 73, "sc" : 3},  {"sl" : 81, "el" : 93, "sc" : 3}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_13" : {
					  "name" : "staticMultiService",
					  "pass" : true,
					  "methods" : [{"sl": 47 },],
					  "statements" : [{"sl": 49 },{"sl": 57 },{"sl": 58 },{"sl": 65 },{"sl": 66 },{"sl": 67 },{"sl": 68 },{"sl": 72 },]
					  },
		"test_11" : {
					  "name" : "testMultiService",
					  "pass" : true,
					  "methods" : [{"sl": 47 },],
					  "statements" : [{"sl": 49 },{"sl": 57 },{"sl": 58 },{"sl": 65 },{"sl": 66 },{"sl": 67 },{"sl": 68 },{"sl": 72 },]
					  },
		"test_4" : {
					  "name" : "staticUnaryService",
					  "pass" : true,
					  "methods" : [{"sl": 47 },],
					  "statements" : [{"sl": 49 },{"sl": 57 },{"sl": 58 },{"sl": 65 },{"sl": 66 },{"sl": 67 },{"sl": 68 },{"sl": 72 },]
					  },
		"test_16" : {
					  "name" : "unleasedUnaryService",
					  "pass" : true,
					  "methods" : [{"sl": 47 },],
					  "statements" : [{"sl": 49 },{"sl": 57 },{"sl": 58 },{"sl": 65 },{"sl": 66 },{"sl": 67 },{"sl": 68 },{"sl": 72 },]
					  },
		"test_15" : {
					  "name" : "testWiring",
					  "pass" : true,
					  "methods" : [{"sl": 81 },],
					  "statements" : [{"sl": 83 },{"sl": 85 },{"sl": 87 },{"sl": 88 },{"sl": 92 },]
					  },
		"test_3" : {
					  "name" : "leasedMultiService",
					  "pass" : true,
					  "methods" : [{"sl": 47 },],
					  "statements" : [{"sl": 49 },{"sl": 57 },{"sl": 58 },{"sl": 65 },{"sl": 66 },{"sl": 67 },{"sl": 68 },{"sl": 72 },]
					  },
		"test_17" : {
					  "name" : "leasedUnaryService",
					  "pass" : true,
					  "methods" : [{"sl": 47 },],
					  "statements" : [{"sl": 49 },{"sl": 57 },{"sl": 58 },{"sl": 65 },{"sl": 66 },{"sl": 67 },{"sl": 68 },{"sl": 72 },]
					  },
		"test_10" : {
					  "name" : "checkInjection",
					  "pass" : true,
					  "methods" : [{"sl": 47 },],
					  "statements" : [{"sl": 49 },{"sl": 57 },{"sl": 58 },{"sl": 59 },{"sl": 60 },{"sl": 65 },{"sl": 66 },{"sl": 67 },{"sl": 68 },{"sl": 72 },]
					  },
		"test_7" : {
					  "name" : "testUnaryService",
					  "pass" : true,
					  "methods" : [{"sl": 47 },],
					  "statements" : [{"sl": 49 },{"sl": 57 },{"sl": 58 },{"sl": 65 },{"sl": 66 },{"sl": 67 },{"sl": 68 },{"sl": 72 },]
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
  [ 10 , 3 , 13 , 17 , 4 , 7 , 16 , 11   ] ,
  [  ] ,
  [ 10 , 3 , 13 , 17 , 4 , 7 , 16 , 11   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 10 , 3 , 13 , 17 , 4 , 7 , 16 , 11   ] ,
  [ 10 , 3 , 13 , 17 , 4 , 7 , 16 , 11   ] ,
  [ 10   ] ,
  [ 10   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 10 , 3 , 13 , 17 , 4 , 7 , 16 , 11   ] ,
  [ 10 , 3 , 13 , 17 , 4 , 7 , 16 , 11   ] ,
  [ 10 , 3 , 13 , 17 , 4 , 7 , 16 , 11   ] ,
  [ 10 , 3 , 13 , 17 , 4 , 7 , 16 , 11   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 10 , 3 , 13 , 17 , 4 , 7 , 16 , 11   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 15   ] ,
  [  ] ,
  [ 15   ] ,
  [  ] ,
  [ 15   ] ,
  [  ] ,
  [ 15   ] ,
  [ 15   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 15   ] ,
  [  ] ,
  [  ] 
];
