var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 3426, "sl" : 26, "el" : 49, "name" : "Stopwatch",
    "methods" : [
              {"sl" : 34, "el" : 41, "sc" : 3},  {"sl" : 46, "el" : 48, "sc" : 3}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_0" : {
					  "name" : "testInjection",
					  "pass" : true ,
					  "methods" : [{"sl": 34 },{"sl": 46 },],
					  "statements" : [{"sl": 35 },{"sl": 36 },{"sl": 37 },{"sl": 39 },{"sl": 47 },]
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
  [ 0   ] ,
  [ 0   ] ,
  [ 0   ] ,
  [ 0   ] ,
  [  ] ,
  [ 0   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 0   ] ,
  [ 0   ] ,
  [  ] ,
  [  ] 
];
