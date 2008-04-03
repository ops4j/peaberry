var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 439, "sl" : 29, "el" : 62, "name" : "TestActivator",
    "methods" : [
              {"sl" : 34, "el" : 54, "sc" : 3},  {"sl" : 40, "el" : 52, "sc" : 7},  {"sl" : 56, "el" : 58, "sc" : 3},  {"sl" : 60, "el" : 61, "sc" : 3}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_12" : {
					  "name" : "run",
					  "pass" : true ,
					  "methods" : [{"sl": 40 },{"sl": 56 },{"sl": 60 },],
					  "statements" : [{"sl": 41 },{"sl": 42 },{"sl": 43 },{"sl": 44 },{"sl": 46 },{"sl": 47 },{"sl": 57 },]
					  },
		"test_1" : {
					  "name" : "getBundleContext",
					  "pass" : true ,
					  "methods" : [{"sl": 56 },],
					  "statements" : [{"sl": 57 },]
					  },
		"test_5" : {
					  "name" : "run",
					  "pass" : true ,
					  "methods" : [{"sl": 56 },],
					  "statements" : [{"sl": 57 },]
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
  [ 12   ] ,
  [ 12   ] ,
  [ 12   ] ,
  [ 12   ] ,
  [ 12   ] ,
  [  ] ,
  [ 12   ] ,
  [ 12   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 12 , 5 , 1   ] ,
  [ 12 , 5 , 1   ] ,
  [  ] ,
  [  ] ,
  [ 12   ] ,
  [  ] ,
  [  ] 
];
