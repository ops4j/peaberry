var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 313, "sl" : 31, "el" : 75, "name" : "PeaberryRunner",
    "methods" : [
              {"sl" : 39, "el" : 69, "sc" : 3},  {"sl" : 44, "el" : 62, "sc" : 7},  {"sl" : 71, "el" : 74, "sc" : 3}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_12" : {
					  "name" : "run",
					  "pass" : true ,
					  "methods" : [{"sl": 39 },{"sl": 44 },{"sl": 71 },],
					  "statements" : [{"sl": 41 },{"sl": 43 },{"sl": 45 },{"sl": 47 },{"sl": 48 },{"sl": 49 },{"sl": 52 },{"sl": 54 },{"sl": 55 },{"sl": 57 },{"sl": 58 },{"sl": 59 },{"sl": 61 },{"sl": 65 },{"sl": 66 },{"sl": 67 },{"sl": 73 },]
					  },
		"test_5" : {
					  "name" : "run",
					  "pass" : true ,
					  "methods" : [{"sl": 44 },{"sl": 71 },],
					  "statements" : [{"sl": 45 },{"sl": 47 },{"sl": 48 },{"sl": 49 },{"sl": 52 },{"sl": 54 },{"sl": 55 },{"sl": 57 },{"sl": 58 },{"sl": 59 },{"sl": 61 },{"sl": 73 },]
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
  [ 12   ] ,
  [  ] ,
  [ 12   ] ,
  [  ] ,
  [ 12   ] ,
  [ 12 , 5   ] ,
  [ 12 , 5   ] ,
  [  ] ,
  [ 12 , 5   ] ,
  [ 12 , 5   ] ,
  [ 12 , 5   ] ,
  [  ] ,
  [  ] ,
  [ 12 , 5   ] ,
  [  ] ,
  [ 12 , 5   ] ,
  [ 12 , 5   ] ,
  [  ] ,
  [ 12 , 5   ] ,
  [ 12 , 5   ] ,
  [ 12 , 5   ] ,
  [  ] ,
  [ 12 , 5   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 12   ] ,
  [ 12   ] ,
  [ 12   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 12 , 5   ] ,
  [  ] ,
  [ 12 , 5   ] ,
  [  ] ,
  [  ] 
];
