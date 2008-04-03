var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 70, "sl" : 31, "el" : 71, "name" : "LeasedServiceRegistry",
    "methods" : [
              {"sl" : 40, "el" : 43, "sc" : 3},  {"sl" : 45, "el" : 70, "sc" : 3}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_6" : {
					  "name" : "run",
					  "pass" : true ,
					  "methods" : [{"sl": 40 },{"sl": 45 },],
					  "statements" : [{"sl": 41 },{"sl": 42 },{"sl": 47 },{"sl": 49 },{"sl": 50 },{"sl": 51 },{"sl": 53 },{"sl": 54 },{"sl": 55 },{"sl": 58 },{"sl": 59 },{"sl": 61 },{"sl": 64 },{"sl": 69 },]
					  },
		"test_14" : {
					  "name" : "leasedMultiService",
					  "pass" : true ,
					  "methods" : [{"sl": 45 },],
					  "statements" : [{"sl": 47 },{"sl": 49 },{"sl": 50 },{"sl": 51 },{"sl": 53 },{"sl": 54 },{"sl": 55 },{"sl": 58 },{"sl": 59 },{"sl": 61 },{"sl": 64 },{"sl": 69 },]
					  },
		"test_3" : {
					  "name" : "testMultiService",
					  "pass" : true ,
					  "methods" : [{"sl": 45 },],
					  "statements" : [{"sl": 47 },{"sl": 49 },{"sl": 50 },{"sl": 51 },{"sl": 53 },{"sl": 54 },{"sl": 55 },{"sl": 58 },{"sl": 59 },{"sl": 61 },{"sl": 64 },{"sl": 69 },]
					  },
		"test_9" : {
					  "name" : "staticMultiService",
					  "pass" : true ,
					  "methods" : [{"sl": 45 },],
					  "statements" : [{"sl": 47 },{"sl": 49 },{"sl": 50 },{"sl": 51 },{"sl": 53 },{"sl": 54 },{"sl": 55 },{"sl": 58 },{"sl": 59 },{"sl": 61 },{"sl": 64 },{"sl": 69 },]
					  },
		"test_5" : {
					  "name" : "leasedUnaryService",
					  "pass" : true ,
					  "methods" : [{"sl": 45 },],
					  "statements" : [{"sl": 47 },{"sl": 49 },{"sl": 50 },{"sl": 51 },{"sl": 53 },{"sl": 54 },{"sl": 55 },{"sl": 58 },{"sl": 59 },{"sl": 61 },{"sl": 64 },{"sl": 69 },]
					  },
		"test_2" : {
					  "name" : "staticUnaryService",
					  "pass" : true ,
					  "methods" : [{"sl": 45 },],
					  "statements" : [{"sl": 47 },{"sl": 49 },{"sl": 50 },{"sl": 51 },{"sl": 53 },{"sl": 54 },{"sl": 55 },{"sl": 58 },{"sl": 59 },{"sl": 61 },{"sl": 64 },{"sl": 69 },]
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
  [ 6   ] ,
  [ 6   ] ,
  [ 6   ] ,
  [  ] ,
  [  ] ,
  [ 5 , 9 , 3 , 2 , 6 , 14   ] ,
  [  ] ,
  [ 5 , 9 , 3 , 2 , 6 , 14   ] ,
  [  ] ,
  [ 5 , 9 , 3 , 2 , 6 , 14   ] ,
  [ 5 , 9 , 3 , 2 , 6 , 14   ] ,
  [ 5 , 9 , 3 , 2 , 6 , 14   ] ,
  [  ] ,
  [ 5 , 9 , 3 , 2 , 6 , 14   ] ,
  [ 5 , 9 , 3 , 2 , 6 , 14   ] ,
  [ 5 , 9 , 3 , 2 , 6 , 14   ] ,
  [  ] ,
  [  ] ,
  [ 5 , 9 , 3 , 2 , 6 , 14   ] ,
  [ 5 , 9 , 3 , 2 , 6 , 14   ] ,
  [  ] ,
  [ 5 , 9 , 3 , 2 , 6 , 14   ] ,
  [  ] ,
  [  ] ,
  [ 5 , 9 , 3 , 2 , 6 , 14   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 5 , 9 , 3 , 2 , 6 , 14   ] ,
  [  ] ,
  [  ] 
];
