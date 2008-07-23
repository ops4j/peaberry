var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 235, "sl" : 33, "el" : 69, "name" : "DirectServiceFactory",
    "methods" : [
              {"sl" : 36, "el" : 36, "sc" : 3},  {"sl" : 38, "el" : 52, "sc" : 3},  {"sl" : 54, "el" : 58, "sc" : 3},  {"sl" : 60, "el" : 68, "sc" : 3}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_11" : {
					  "name" : "brokenServices",
					  "pass" : true,
					  "methods" : [{"sl": 38 },{"sl": 60 },],
					  "statements" : [{"sl": 41 },{"sl": 42 },{"sl": 44 },{"sl": 45 },{"sl": 46 },{"sl": 51 },{"sl": 63 },{"sl": 64 },{"sl": 66 },]
					  },
		"test_6" : {
					  "name" : "testWiring",
					  "pass" : true,
					  "methods" : [{"sl": 38 },{"sl": 54 },{"sl": 60 },],
					  "statements" : [{"sl": 41 },{"sl": 42 },{"sl": 44 },{"sl": 45 },{"sl": 46 },{"sl": 47 },{"sl": 51 },{"sl": 57 },{"sl": 63 },{"sl": 64 },{"sl": 66 },]
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
  [ 6 , 11   ] ,
  [  ] ,
  [  ] ,
  [ 6 , 11   ] ,
  [ 6 , 11   ] ,
  [  ] ,
  [ 6 , 11   ] ,
  [ 6 , 11   ] ,
  [ 6 , 11   ] ,
  [ 6   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 6 , 11   ] ,
  [  ] ,
  [  ] ,
  [ 6   ] ,
  [  ] ,
  [  ] ,
  [ 6   ] ,
  [  ] ,
  [  ] ,
  [ 6 , 11   ] ,
  [  ] ,
  [  ] ,
  [ 6 , 11   ] ,
  [ 6 , 11   ] ,
  [  ] ,
  [ 6 , 11   ] ,
  [  ] ,
  [  ] ,
  [  ] 
];
