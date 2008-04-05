var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 326, "sl" : 29, "el" : 69, "name" : "ServiceInjectionTests",
    "methods" : [
              {"sl" : 39, "el" : 41, "sc" : 3},  {"sl" : 43, "el" : 48, "sc" : 3},  {"sl" : 52, "el" : 57, "sc" : 3},  {"sl" : 59, "el" : 68, "sc" : 3}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_13" : {
					  "name" : "checkInjection",
					  "pass" : true ,
					  "methods" : [{"sl": 59 },],
					  "statements" : [{"sl": 60 },{"sl": 61 },{"sl": 62 },{"sl": 63 },{"sl": 64 },{"sl": 65 },{"sl": 66 },{"sl": 67 },]
					  },
		"test_21" : {
					  "name" : "run",
					  "pass" : true ,
					  "methods" : [{"sl": 43 },{"sl": 52 },{"sl": 59 },],
					  "statements" : [{"sl": 47 },{"sl": 56 },{"sl": 60 },{"sl": 61 },{"sl": 62 },{"sl": 63 },{"sl": 64 },{"sl": 65 },{"sl": 66 },{"sl": 67 },]
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
  [  ] ,
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
  [  ] ,
  [  ] ,
  [ 21 , 13   ] ,
  [ 21 , 13   ] ,
  [ 21 , 13   ] ,
  [ 21 , 13   ] ,
  [ 21 , 13   ] ,
  [ 21 , 13   ] ,
  [ 21 , 13   ] ,
  [ 21 , 13   ] ,
  [ 21 , 13   ] ,
  [  ] ,
  [  ] 
];
