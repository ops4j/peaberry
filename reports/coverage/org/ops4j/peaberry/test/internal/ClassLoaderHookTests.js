var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 316, "sl" : 28, "el" : 40, "name" : "ClassLoaderHookTests",
    "methods" : [
              {"sl" : 30, "el" : 39, "sc" : 3}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_12" : {
					  "name" : "standardDelegation",
					  "pass" : true ,
					  "methods" : [{"sl": 30 },],
					  "statements" : [{"sl": 32 },{"sl": 33 },{"sl": 35 },{"sl": 37 },{"sl": 38 },]
					  },
		"test_21" : {
					  "name" : "run",
					  "pass" : true ,
					  "methods" : [{"sl": 30 },],
					  "statements" : [{"sl": 32 },{"sl": 33 },{"sl": 35 },{"sl": 37 },{"sl": 38 },]
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
  [ 21 , 12   ] ,
  [  ] ,
  [ 21 , 12   ] ,
  [ 21 , 12   ] ,
  [  ] ,
  [ 21 , 12   ] ,
  [  ] ,
  [ 21 , 12   ] ,
  [ 21 , 12   ] ,
  [  ] ,
  [  ] 
];