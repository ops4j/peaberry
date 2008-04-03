var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 238, "sl" : 30, "el" : 57, "name" : "TestActivator",
    "methods" : [
              {"sl" : 33, "el" : 53, "sc" : 3},  {"sl" : 37, "el" : 51, "sc" : 7},  {"sl" : 55, "el" : 56, "sc" : 3}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_6" : {
					  "name" : "run",
					  "pass" : true ,
					  "methods" : [{"sl": 37 },{"sl": 55 },],
					  "statements" : [{"sl": 38 },{"sl": 39 },{"sl": 40 },{"sl": 41 },{"sl": 42 },{"sl": 43 },{"sl": 45 },{"sl": 46 },]
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
  [ 6   ] ,
  [ 6   ] ,
  [ 6   ] ,
  [ 6   ] ,
  [ 6   ] ,
  [ 6   ] ,
  [ 6   ] ,
  [  ] ,
  [ 6   ] ,
  [ 6   ] ,
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
  [  ] 
];
