var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 20, "sl" : 30, "el" : 85, "name" : "NonDelegatingClassLoaderHook",
    "methods" : [
              {"sl" : 43, "el" : 69, "sc" : 9},  {"sl" : 50, "el" : 67, "sc" : 13},  {"sl" : 75, "el" : 84, "sc" : 3}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_12" : {
					  "name" : "standardDelegation",
					  "pass" : true ,
					  "methods" : [{"sl": 75 },],
					  "statements" : [{"sl": 76 },{"sl": 79 },{"sl": 80 },]
					  },
		"test_21" : {
					  "name" : "run",
					  "pass" : true ,
					  "methods" : [{"sl": 43 },{"sl": 50 },{"sl": 75 },],
					  "statements" : [{"sl": 45 },{"sl": 54 },{"sl": 55 },{"sl": 56 },{"sl": 57 },{"sl": 60 },{"sl": 66 },{"sl": 76 },{"sl": 79 },{"sl": 80 },{"sl": 83 },]
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
  [ 21   ] ,
  [  ] ,
  [ 21   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 21   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 21   ] ,
  [ 21   ] ,
  [ 21   ] ,
  [ 21   ] ,
  [  ] ,
  [  ] ,
  [ 21   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 21   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 21 , 12   ] ,
  [ 21 , 12   ] ,
  [  ] ,
  [  ] ,
  [ 21 , 12   ] ,
  [ 21 , 12   ] ,
  [  ] ,
  [  ] ,
  [ 21   ] ,
  [  ] ,
  [  ] 
];
