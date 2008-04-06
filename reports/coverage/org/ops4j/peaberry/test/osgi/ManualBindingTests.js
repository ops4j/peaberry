var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 290, "sl" : 39, "el" : 90, "name" : "ManualBindingTests",
    "methods" : [
              {"sl" : 42, "el" : 60, "sc" : 3},  {"sl" : 68, "el" : 71, "sc" : 3},  {"sl" : 73, "el" : 77, "sc" : 3},  {"sl" : 79, "el" : 89, "sc" : 3}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_6" : {
					  "name" : "run",
					  "pass" : true ,
					  "methods" : [{"sl": 42 },{"sl": 68 },{"sl": 73 },{"sl": 79 },],
					  "statements" : [{"sl": 45 },{"sl": 47 },{"sl": 49 },{"sl": 50 },{"sl": 52 },{"sl": 55 },{"sl": 59 },{"sl": 69 },{"sl": 70 },{"sl": 74 },{"sl": 75 },{"sl": 76 },{"sl": 80 },{"sl": 81 },{"sl": 82 },{"sl": 83 },{"sl": 84 },{"sl": 85 },{"sl": 86 },{"sl": 87 },{"sl": 88 },]
					  },
		"test_11" : {
					  "name" : "testAnnotations",
					  "pass" : true ,
					  "methods" : [{"sl": 68 },],
					  "statements" : [{"sl": 69 },{"sl": 70 },]
					  },
		"test_18" : {
					  "name" : "testUnaryService",
					  "pass" : true ,
					  "methods" : [{"sl": 73 },],
					  "statements" : [{"sl": 74 },{"sl": 75 },{"sl": 76 },]
					  },
		"test_12" : {
					  "name" : "testMultiService",
					  "pass" : true ,
					  "methods" : [{"sl": 79 },],
					  "statements" : [{"sl": 80 },{"sl": 81 },{"sl": 82 },{"sl": 83 },{"sl": 84 },{"sl": 85 },{"sl": 86 },{"sl": 87 },{"sl": 88 },]
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
  [ 6   ] ,
  [  ] ,
  [  ] ,
  [ 6   ] ,
  [  ] ,
  [ 6   ] ,
  [  ] ,
  [ 6   ] ,
  [ 6   ] ,
  [  ] ,
  [ 6   ] ,
  [  ] ,
  [  ] ,
  [ 6   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 6   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 6 , 11   ] ,
  [ 6 , 11   ] ,
  [ 6 , 11   ] ,
  [  ] ,
  [  ] ,
  [ 18 , 6   ] ,
  [ 18 , 6   ] ,
  [ 18 , 6   ] ,
  [ 18 , 6   ] ,
  [  ] ,
  [  ] ,
  [ 12 , 6   ] ,
  [ 12 , 6   ] ,
  [ 12 , 6   ] ,
  [ 12 , 6   ] ,
  [ 12 , 6   ] ,
  [ 12 , 6   ] ,
  [ 12 , 6   ] ,
  [ 12 , 6   ] ,
  [ 12 , 6   ] ,
  [ 12 , 6   ] ,
  [  ] ,
  [  ] 
];
