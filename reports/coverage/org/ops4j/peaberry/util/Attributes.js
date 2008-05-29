var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 74, "sl" : 36, "el" : 98, "name" : "Attributes",
    "methods" : [
              {"sl" : 39, "el" : 39, "sc" : 3},  {"sl" : 47, "el" : 73, "sc" : 3},  {"sl" : 81, "el" : 97, "sc" : 3}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_17" : {
					  "name" : "testUnaryService",
					  "pass" : true,
					  "methods" : [{"sl": 47 },],
					  "statements" : [{"sl": 49 },{"sl": 57 },{"sl": 58 },{"sl": 59 },{"sl": 60 },{"sl": 65 },{"sl": 66 },{"sl": 67 },{"sl": 68 },{"sl": 72 },]
					  },
		"test_14" : {
					  "name" : "testAnnotationConverter",
					  "pass" : true,
					  "methods" : [{"sl": 81 },],
					  "statements" : [{"sl": 83 },{"sl": 84 },{"sl": 87 },{"sl": 88 },{"sl": 89 },{"sl": 90 },{"sl": 92 },{"sl": 96 },]
					  },
		"test_2" : {
					  "name" : "testMultiService",
					  "pass" : true,
					  "methods" : [{"sl": 47 },],
					  "statements" : [{"sl": 49 },{"sl": 57 },{"sl": 58 },{"sl": 59 },{"sl": 60 },{"sl": 65 },{"sl": 66 },{"sl": 67 },{"sl": 68 },{"sl": 72 },]
					  },
		"test_18" : {
					  "name" : "leasedMultiService",
					  "pass" : true,
					  "methods" : [{"sl": 47 },],
					  "statements" : [{"sl": 49 },{"sl": 57 },{"sl": 58 },{"sl": 59 },{"sl": 60 },{"sl": 65 },{"sl": 66 },{"sl": 67 },{"sl": 68 },{"sl": 72 },]
					  },
		"test_6" : {
					  "name" : "unleasedUnaryService",
					  "pass" : true,
					  "methods" : [{"sl": 47 },],
					  "statements" : [{"sl": 49 },{"sl": 57 },{"sl": 58 },{"sl": 59 },{"sl": 60 },{"sl": 65 },{"sl": 66 },{"sl": 67 },{"sl": 68 },{"sl": 72 },]
					  },
		"test_12" : {
					  "name" : "staticMultiService",
					  "pass" : true,
					  "methods" : [{"sl": 47 },],
					  "statements" : [{"sl": 49 },{"sl": 57 },{"sl": 58 },{"sl": 59 },{"sl": 60 },{"sl": 65 },{"sl": 66 },{"sl": 67 },{"sl": 68 },{"sl": 72 },]
					  },
		"test_9" : {
					  "name" : "testPropertyConverter",
					  "pass" : true,
					  "methods" : [{"sl": 47 },],
					  "statements" : [{"sl": 49 },{"sl": 57 },{"sl": 58 },{"sl": 65 },{"sl": 66 },{"sl": 67 },{"sl": 68 },{"sl": 72 },]
					  },
		"test_0" : {
					  "name" : "checkInjection",
					  "pass" : true,
					  "methods" : [{"sl": 47 },],
					  "statements" : [{"sl": 49 },{"sl": 57 },{"sl": 58 },{"sl": 59 },{"sl": 60 },{"sl": 65 },{"sl": 66 },{"sl": 67 },{"sl": 68 },{"sl": 72 },]
					  },
		"test_16" : {
					  "name" : "staticUnaryService",
					  "pass" : true,
					  "methods" : [{"sl": 47 },],
					  "statements" : [{"sl": 49 },{"sl": 57 },{"sl": 58 },{"sl": 59 },{"sl": 60 },{"sl": 65 },{"sl": 66 },{"sl": 67 },{"sl": 68 },{"sl": 72 },]
					  },
		"test_15" : {
					  "name" : "leasedUnaryService",
					  "pass" : true,
					  "methods" : [{"sl": 47 },],
					  "statements" : [{"sl": 49 },{"sl": 57 },{"sl": 58 },{"sl": 59 },{"sl": 60 },{"sl": 65 },{"sl": 66 },{"sl": 67 },{"sl": 68 },{"sl": 72 },]
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
  [ 16 , 0 , 9 , 2 , 12 , 6 , 17 , 18 , 15   ] ,
  [  ] ,
  [ 16 , 0 , 9 , 2 , 12 , 6 , 17 , 18 , 15   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 16 , 0 , 9 , 2 , 12 , 6 , 17 , 18 , 15   ] ,
  [ 16 , 0 , 9 , 2 , 12 , 6 , 17 , 18 , 15   ] ,
  [ 16 , 0 , 2 , 12 , 6 , 17 , 18 , 15   ] ,
  [ 16 , 0 , 2 , 12 , 6 , 17 , 18 , 15   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 16 , 0 , 9 , 2 , 12 , 6 , 17 , 18 , 15   ] ,
  [ 16 , 0 , 9 , 2 , 12 , 6 , 17 , 18 , 15   ] ,
  [ 16 , 0 , 9 , 2 , 12 , 6 , 17 , 18 , 15   ] ,
  [ 16 , 0 , 9 , 2 , 12 , 6 , 17 , 18 , 15   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 16 , 0 , 9 , 2 , 12 , 6 , 17 , 18 , 15   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 14   ] ,
  [  ] ,
  [ 14   ] ,
  [ 14   ] ,
  [  ] ,
  [  ] ,
  [ 14   ] ,
  [ 14   ] ,
  [ 14   ] ,
  [ 14   ] ,
  [  ] ,
  [ 14   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 14   ] ,
  [  ] ,
  [  ] 
];
