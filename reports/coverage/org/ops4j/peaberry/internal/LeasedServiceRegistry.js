var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 100, "sl" : 31, "el" : 71, "name" : "LeasedServiceRegistry",
    "methods" : [
              {"sl" : 41, "el" : 44, "sc" : 3},  {"sl" : 46, "el" : 70, "sc" : 3}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_0" : {
					  "name" : "leasedUnaryService",
					  "pass" : true ,
					  "methods" : [{"sl": 46 },],
					  "statements" : [{"sl": 49 },{"sl": 50 },{"sl": 51 },{"sl": 53 },{"sl": 54 },{"sl": 55 },{"sl": 58 },{"sl": 59 },{"sl": 61 },{"sl": 64 },{"sl": 69 },]
					  },
		"test_3" : {
					  "name" : "leasedMultiService",
					  "pass" : true ,
					  "methods" : [{"sl": 46 },],
					  "statements" : [{"sl": 49 },{"sl": 50 },{"sl": 51 },{"sl": 53 },{"sl": 54 },{"sl": 55 },{"sl": 58 },{"sl": 59 },{"sl": 61 },{"sl": 64 },{"sl": 69 },]
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
  [ 3 , 0   ] ,
  [  ] ,
  [  ] ,
  [ 3 , 0   ] ,
  [ 3 , 0   ] ,
  [ 3 , 0   ] ,
  [  ] ,
  [ 3 , 0   ] ,
  [ 3 , 0   ] ,
  [ 3 , 0   ] ,
  [  ] ,
  [  ] ,
  [ 3 , 0   ] ,
  [ 3 , 0   ] ,
  [  ] ,
  [ 3 , 0   ] ,
  [  ] ,
  [  ] ,
  [ 3 , 0   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 3 , 0   ] ,
  [  ] ,
  [  ] 
];
