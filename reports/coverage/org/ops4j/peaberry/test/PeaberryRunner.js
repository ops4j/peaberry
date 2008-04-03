var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 360, "sl" : 31, "el" : 75, "name" : "PeaberryRunner",
    "methods" : [
              {"sl" : 38, "el" : 69, "sc" : 3},  {"sl" : 43, "el" : 62, "sc" : 7},  {"sl" : 71, "el" : 74, "sc" : 3}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_6" : {
					  "name" : "run",
					  "pass" : true ,
					  "methods" : [{"sl": 38 },{"sl": 43 },{"sl": 71 },],
					  "statements" : [{"sl": 40 },{"sl": 42 },{"sl": 45 },{"sl": 47 },{"sl": 48 },{"sl": 49 },{"sl": 52 },{"sl": 54 },{"sl": 55 },{"sl": 57 },{"sl": 58 },{"sl": 59 },{"sl": 61 },{"sl": 65 },{"sl": 66 },{"sl": 67 },{"sl": 73 },]
					  },
		"test_7" : {
					  "name" : "run",
					  "pass" : true ,
					  "methods" : [{"sl": 43 },{"sl": 71 },],
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
  [ 6   ] ,
  [  ] ,
  [ 6   ] ,
  [  ] ,
  [ 6   ] ,
  [ 7 , 6   ] ,
  [  ] ,
  [ 7 , 6   ] ,
  [  ] ,
  [ 7 , 6   ] ,
  [ 7 , 6   ] ,
  [ 7 , 6   ] ,
  [  ] ,
  [  ] ,
  [ 7 , 6   ] ,
  [  ] ,
  [ 7 , 6   ] ,
  [ 7 , 6   ] ,
  [  ] ,
  [ 7 , 6   ] ,
  [ 7 , 6   ] ,
  [ 7 , 6   ] ,
  [  ] ,
  [ 7 , 6   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 6   ] ,
  [ 6   ] ,
  [ 6   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 7 , 6   ] ,
  [  ] ,
  [ 7 , 6   ] ,
  [  ] ,
  [  ] 
];
