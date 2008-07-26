var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 113, "sl" : 33, "el" : 71, "name" : "DirectServiceFactory",
    "methods" : [
              {"sl" : 36, "el" : 36, "sc" : 3},  {"sl" : 38, "el" : 53, "sc" : 3},  {"sl" : 55, "el" : 60, "sc" : 3},  {"sl" : 62, "el" : 70, "sc" : 3}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_15" : {
					  "name" : "testWiring",
					  "pass" : true,
					  "methods" : [{"sl": 38 },{"sl": 55 },{"sl": 62 },],
					  "statements" : [{"sl": 41 },{"sl": 42 },{"sl": 44 },{"sl": 46 },{"sl": 47 },{"sl": 48 },{"sl": 52 },{"sl": 59 },{"sl": 65 },{"sl": 66 },{"sl": 68 },]
					  },
		"test_12" : {
					  "name" : "brokenServices",
					  "pass" : true,
					  "methods" : [{"sl": 38 },{"sl": 62 },],
					  "statements" : [{"sl": 41 },{"sl": 42 },{"sl": 44 },{"sl": 46 },{"sl": 47 },{"sl": 52 },{"sl": 65 },{"sl": 66 },{"sl": 68 },]
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
  [ 15 , 12   ] ,
  [  ] ,
  [  ] ,
  [ 15 , 12   ] ,
  [ 15 , 12   ] ,
  [  ] ,
  [ 15 , 12   ] ,
  [  ] ,
  [ 15 , 12   ] ,
  [ 15 , 12   ] ,
  [ 15   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 15 , 12   ] ,
  [  ] ,
  [  ] ,
  [ 15   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 15   ] ,
  [  ] ,
  [  ] ,
  [ 15 , 12   ] ,
  [  ] ,
  [  ] ,
  [ 15 , 12   ] ,
  [ 15 , 12   ] ,
  [  ] ,
  [ 15 , 12   ] ,
  [  ] ,
  [  ] ,
  [  ] 
];
