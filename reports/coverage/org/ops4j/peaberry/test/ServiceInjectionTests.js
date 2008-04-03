var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 210, "sl" : 30, "el" : 76, "name" : "ServiceInjectionTests",
    "methods" : [
              {"sl" : 34, "el" : 36, "sc" : 3},  {"sl" : 45, "el" : 47, "sc" : 3},  {"sl" : 49, "el" : 54, "sc" : 3},  {"sl" : 58, "el" : 63, "sc" : 3},  {"sl" : 65, "el" : 75, "sc" : 3}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_6" : {
					  "name" : "run",
					  "pass" : true ,
					  "methods" : [{"sl": 34 },{"sl": 45 },{"sl": 49 },{"sl": 58 },{"sl": 65 },],
					  "statements" : [{"sl": 35 },{"sl": 46 },{"sl": 53 },{"sl": 62 },{"sl": 67 },{"sl": 68 },{"sl": 69 },{"sl": 70 },{"sl": 71 },{"sl": 72 },{"sl": 73 },{"sl": 74 },]
					  },
		"test_15" : {
					  "name" : "checkInjection",
					  "pass" : true ,
					  "methods" : [{"sl": 65 },],
					  "statements" : [{"sl": 67 },{"sl": 68 },{"sl": 69 },{"sl": 70 },{"sl": 71 },{"sl": 72 },{"sl": 73 },{"sl": 74 },]
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
  [  ] ,
  [ 6   ] ,
  [ 6   ] ,
  [  ] ,
  [  ] ,
  [ 6   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 6   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 6   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 6   ] ,
  [  ] ,
  [  ] ,
  [ 15 , 6   ] ,
  [  ] ,
  [ 15 , 6   ] ,
  [ 15 , 6   ] ,
  [ 15 , 6   ] ,
  [ 15 , 6   ] ,
  [ 15 , 6   ] ,
  [ 15 , 6   ] ,
  [ 15 , 6   ] ,
  [ 15 , 6   ] ,
  [  ] ,
  [  ] 
];
