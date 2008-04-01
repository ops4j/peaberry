var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 4072, "sl" : 40, "el" : 74, "name" : "OSGiServiceBindingTest",
    "methods" : [
             {"sl" : 53, "el" : 55, "sc" : 3},  {"sl" : 66, "el" : 73, "sc" : 3}  ]}
    ,
    {"id" : 4072, "sl" : 47, "el" : 47, "name" : "OSGiServiceBindingTest.TestService",
    "methods" : [
             ]}
    ,
    {"id" : 4074, "sl" : 57, "el" : 64, "name" : "OSGiServiceBindingTest.Module",
    "methods" : [
              {"sl" : 60, "el" : 63, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_0" : {
					  "name" : "testInjection",
					  "pass" : true ,
					  "methods" : [{"sl": 53 },{"sl": 60 },{"sl": 66 },],
					  "statements" : [{"sl": 54 },{"sl": 62 },{"sl": 69 },{"sl": 70 },{"sl": 72 },]
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
  [ 0   ] ,
  [ 0   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 0   ] ,
  [  ] ,
  [ 0   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 0   ] ,
  [  ] ,
  [  ] ,
  [ 0   ] ,
  [ 0   ] ,
  [  ] ,
  [ 0   ] ,
  [  ] ,
  [  ] 
];
