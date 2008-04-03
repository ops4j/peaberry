var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 79, "sl" : 31, "el" : 65, "name" : "StaticServiceRegistry",
    "methods" : [
              {"sl" : 38, "el" : 40, "sc" : 3},  {"sl" : 42, "el" : 64, "sc" : 3}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_8" : {
					  "name" : "staticMultiService",
					  "pass" : true ,
					  "methods" : [{"sl": 42 },],
					  "statements" : [{"sl": 45 },{"sl": 46 },{"sl": 47 },{"sl": 49 },{"sl": 50 },{"sl": 51 },{"sl": 54 },{"sl": 55 },{"sl": 58 },{"sl": 63 },]
					  },
		"test_12" : {
					  "name" : "run",
					  "pass" : true ,
					  "methods" : [{"sl": 38 },{"sl": 42 },],
					  "statements" : [{"sl": 39 },{"sl": 45 },{"sl": 46 },{"sl": 47 },{"sl": 49 },{"sl": 50 },{"sl": 51 },{"sl": 54 },{"sl": 55 },{"sl": 58 },{"sl": 63 },]
					  },
		"test_0" : {
					  "name" : "staticUnaryService",
					  "pass" : true ,
					  "methods" : [{"sl": 42 },],
					  "statements" : [{"sl": 45 },{"sl": 46 },{"sl": 47 },{"sl": 49 },{"sl": 50 },{"sl": 51 },{"sl": 54 },{"sl": 55 },{"sl": 58 },{"sl": 63 },]
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
  [ 12   ] ,
  [ 12   ] ,
  [  ] ,
  [  ] ,
  [ 12 , 0 , 8   ] ,
  [  ] ,
  [  ] ,
  [ 12 , 0 , 8   ] ,
  [ 12 , 0 , 8   ] ,
  [ 12 , 0 , 8   ] ,
  [  ] ,
  [ 12 , 0 , 8   ] ,
  [ 12 , 0 , 8   ] ,
  [ 12 , 0 , 8   ] ,
  [  ] ,
  [  ] ,
  [ 12 , 0 , 8   ] ,
  [ 12 , 0 , 8   ] ,
  [  ] ,
  [  ] ,
  [ 12 , 0 , 8   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 12 , 0 , 8   ] ,
  [  ] ,
  [  ] 
];
