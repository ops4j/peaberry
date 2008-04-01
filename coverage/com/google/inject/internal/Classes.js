var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 87, "sl" : 24, "el" : 35, "name" : "Classes",
    "methods" : [
              {"sl" : 26, "el" : 29, "sc" : 3},  {"sl" : 31, "el" : 34, "sc" : 3}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_0" : {
					  "name" : "testInjection",
					  "pass" : true ,
					  "methods" : [{"sl": 26 },{"sl": 31 },],
					  "statements" : [{"sl": 27 },{"sl": 32 },{"sl": 33 },]
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
  [ 0   ] ,
  [ 0   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 0   ] ,
  [ 0   ] ,
  [ 0   ] ,
  [  ] ,
  [  ] 
];
