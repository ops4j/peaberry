var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 70, "sl" : 31, "el" : 78, "name" : "LeasedServiceRegistry",
    "methods" : [
              {"sl" : 40, "el" : 43, "sc" : 3},  {"sl" : 45, "el" : 77, "sc" : 3}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_20" : {
					  "name" : "staticMultiService",
					  "pass" : true ,
					  "methods" : [{"sl": 45 },],
					  "statements" : [{"sl": 47 },{"sl": 50 },{"sl": 51 },{"sl": 56 },{"sl": 57 },{"sl": 58 },{"sl": 59 },{"sl": 64 },{"sl": 65 },{"sl": 67 },{"sl": 71 },{"sl": 76 },]
					  },
		"test_17" : {
					  "name" : "staticUnaryService",
					  "pass" : true ,
					  "methods" : [{"sl": 45 },],
					  "statements" : [{"sl": 47 },{"sl": 50 },{"sl": 51 },{"sl": 56 },{"sl": 57 },{"sl": 58 },{"sl": 59 },{"sl": 64 },{"sl": 65 },{"sl": 67 },{"sl": 71 },{"sl": 76 },]
					  },
		"test_16" : {
					  "name" : "leasedUnaryService",
					  "pass" : true ,
					  "methods" : [{"sl": 45 },],
					  "statements" : [{"sl": 47 },{"sl": 50 },{"sl": 51 },{"sl": 56 },{"sl": 57 },{"sl": 58 },{"sl": 59 },{"sl": 64 },{"sl": 65 },{"sl": 67 },{"sl": 71 },{"sl": 76 },]
					  },
		"test_14" : {
					  "name" : "testMultiService",
					  "pass" : true ,
					  "methods" : [{"sl": 45 },],
					  "statements" : [{"sl": 47 },{"sl": 50 },{"sl": 51 },{"sl": 56 },{"sl": 57 },{"sl": 58 },{"sl": 59 },{"sl": 64 },{"sl": 65 },{"sl": 67 },{"sl": 71 },{"sl": 76 },]
					  },
		"test_6" : {
					  "name" : "leasedMultiService",
					  "pass" : true ,
					  "methods" : [{"sl": 45 },],
					  "statements" : [{"sl": 47 },{"sl": 50 },{"sl": 51 },{"sl": 56 },{"sl": 57 },{"sl": 58 },{"sl": 59 },{"sl": 64 },{"sl": 65 },{"sl": 67 },{"sl": 71 },{"sl": 76 },]
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
  [ 17 , 14 , 16 , 6 , 20   ] ,
  [  ] ,
  [ 17 , 14 , 16 , 6 , 20   ] ,
  [  ] ,
  [  ] ,
  [ 17 , 14 , 16 , 6 , 20   ] ,
  [ 17 , 14 , 16 , 6 , 20   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 17 , 14 , 16 , 6 , 20   ] ,
  [ 17 , 14 , 16 , 6 , 20   ] ,
  [ 17 , 14 , 16 , 6 , 20   ] ,
  [ 17 , 14 , 16 , 6 , 20   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 17 , 14 , 16 , 6 , 20   ] ,
  [ 17 , 14 , 16 , 6 , 20   ] ,
  [  ] ,
  [ 17 , 14 , 16 , 6 , 20   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 17 , 14 , 16 , 6 , 20   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 17 , 14 , 16 , 6 , 20   ] ,
  [  ] ,
  [  ] 
];
