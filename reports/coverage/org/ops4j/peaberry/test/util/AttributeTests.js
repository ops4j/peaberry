var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 669, "sl" : 33, "el" : 59, "name" : "AttributeTests",
    "methods" : [
              {"sl" : 35, "el" : 49, "sc" : 3},  {"sl" : 51, "el" : 58, "sc" : 3}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_3" : {
					  "name" : "testNameConverter",
					  "pass" : true,
					  "methods" : [{"sl": 51 },],
					  "statements" : [{"sl": 52 },{"sl": 54 },{"sl": 55 },{"sl": 57 },]
					  },
		"test_8" : {
					  "name" : "testPropertyConverter",
					  "pass" : true,
					  "methods" : [{"sl": 35 },],
					  "statements" : [{"sl": 36 },{"sl": 38 },{"sl": 39 },{"sl": 40 },{"sl": 42 },{"sl": 44 },{"sl": 45 },{"sl": 46 },{"sl": 48 },]
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
  [ 8   ] ,
  [ 8   ] ,
  [  ] ,
  [ 8   ] ,
  [ 8   ] ,
  [ 8   ] ,
  [  ] ,
  [ 8   ] ,
  [  ] ,
  [ 8   ] ,
  [ 8   ] ,
  [ 8   ] ,
  [  ] ,
  [ 8   ] ,
  [  ] ,
  [  ] ,
  [ 3   ] ,
  [ 3   ] ,
  [  ] ,
  [ 3   ] ,
  [ 3   ] ,
  [  ] ,
  [ 3   ] ,
  [  ] ,
  [  ] 
];
