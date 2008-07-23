var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 594, "sl" : 36, "el" : 94, "name" : "ServiceContentionTests",
    "methods" : [
             {"sl" : 39, "el" : 46, "sc" : 3},  {"sl" : 70, "el" : 93, "sc" : 3},  {"sl" : 76, "el" : 78, "sc" : 9}  ]}
    ,
    {"id" : 597, "sl" : 48, "el" : 50, "name" : "ServiceContentionTests.DummyService",
    "methods" : [
             ]}
    ,
    {"id" : 597, "sl" : 52, "el" : 62, "name" : "ServiceContentionTests.DummyServiceImpl",
    "methods" : [
              {"sl" : 55, "el" : 61, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_10" : {
					  "name" : "run",
					  "pass" : true,
					  "methods" : [{"sl": 55 },{"sl": 76 },],
					  "statements" : [{"sl": 56 },{"sl": 57 },{"sl": 60 },{"sl": 77 },{"sl": 87 },{"sl": 88 },]
					  },
		"test_0" : {
					  "name" : "testContention",
					  "pass" : true,
					  "methods" : [{"sl": 55 },{"sl": 70 },{"sl": 76 },],
					  "statements" : [{"sl": 56 },{"sl": 57 },{"sl": 60 },{"sl": 72 },{"sl": 74 },{"sl": 75 },{"sl": 77 },{"sl": 82 },{"sl": 83 },{"sl": 86 },{"sl": 87 },{"sl": 88 },{"sl": 92 },]
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
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 10 , 0   ] ,
  [ 10 , 0   ] ,
  [ 10 , 0   ] ,
  [  ] ,
  [  ] ,
  [ 10 , 0   ] ,
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
  [  ] ,
  [ 0   ] ,
  [  ] ,
  [ 0   ] ,
  [ 0   ] ,
  [ 10 , 0   ] ,
  [ 10 , 0   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 0   ] ,
  [ 0   ] ,
  [  ] ,
  [  ] ,
  [ 0   ] ,
  [ 10 , 0   ] ,
  [ 10 , 0   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 0   ] ,
  [  ] ,
  [  ] 
];
