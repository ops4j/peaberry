var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 414, "sl" : 38, "el" : 88, "name" : "ManualBindingTest",
    "methods" : [
              {"sl" : 42, "el" : 61, "sc" : 3},  {"sl" : 69, "el" : 74, "sc" : 3},  {"sl" : 76, "el" : 87, "sc" : 3}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_2" : {
					  "name" : "testUnaryService",
					  "pass" : true ,
					  "methods" : [{"sl": 69 },],
					  "statements" : [{"sl": 71 },{"sl": 72 },{"sl": 73 },]
					  },
		"test_12" : {
					  "name" : "run",
					  "pass" : true ,
					  "methods" : [{"sl": 42 },{"sl": 69 },{"sl": 76 },],
					  "statements" : [{"sl": 44 },{"sl": 45 },{"sl": 46 },{"sl": 48 },{"sl": 49 },{"sl": 51 },{"sl": 54 },{"sl": 55 },{"sl": 57 },{"sl": 60 },{"sl": 71 },{"sl": 72 },{"sl": 73 },{"sl": 78 },{"sl": 79 },{"sl": 80 },{"sl": 81 },{"sl": 82 },{"sl": 83 },{"sl": 84 },{"sl": 85 },{"sl": 86 },]
					  },
		"test_4" : {
					  "name" : "testMultiService",
					  "pass" : true ,
					  "methods" : [{"sl": 76 },],
					  "statements" : [{"sl": 78 },{"sl": 79 },{"sl": 80 },{"sl": 81 },{"sl": 82 },{"sl": 83 },{"sl": 84 },{"sl": 85 },{"sl": 86 },]
					  },
		"test_5" : {
					  "name" : "run",
					  "pass" : true ,
					  "methods" : [{"sl": 42 },{"sl": 69 },{"sl": 76 },],
					  "statements" : [{"sl": 44 },{"sl": 45 },{"sl": 46 },{"sl": 48 },{"sl": 49 },{"sl": 51 },{"sl": 54 },{"sl": 55 },{"sl": 57 },{"sl": 60 },{"sl": 71 },{"sl": 72 },{"sl": 73 },{"sl": 78 },{"sl": 79 },{"sl": 80 },{"sl": 81 },{"sl": 82 },{"sl": 83 },{"sl": 84 },{"sl": 85 },{"sl": 86 },]
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
  [ 12 , 5   ] ,
  [  ] ,
  [ 12 , 5   ] ,
  [ 12 , 5   ] ,
  [ 12 , 5   ] ,
  [  ] ,
  [ 12 , 5   ] ,
  [ 12 , 5   ] ,
  [  ] ,
  [ 12 , 5   ] ,
  [  ] ,
  [  ] ,
  [ 12 , 5   ] ,
  [ 12 , 5   ] ,
  [  ] ,
  [ 12 , 5   ] ,
  [  ] ,
  [  ] ,
  [ 12 , 5   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 12 , 5 , 2   ] ,
  [  ] ,
  [ 12 , 5 , 2   ] ,
  [ 12 , 5 , 2   ] ,
  [ 12 , 5 , 2   ] ,
  [  ] ,
  [  ] ,
  [ 12 , 5 , 4   ] ,
  [  ] ,
  [ 12 , 5 , 4   ] ,
  [ 12 , 5 , 4   ] ,
  [ 12 , 5 , 4   ] ,
  [ 12 , 5 , 4   ] ,
  [ 12 , 5 , 4   ] ,
  [ 12 , 5 , 4   ] ,
  [ 12 , 5 , 4   ] ,
  [ 12 , 5 , 4   ] ,
  [ 12 , 5 , 4   ] ,
  [  ] ,
  [  ] 
];
