var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 121, "sl" : 33, "el" : 70, "name" : "BestServiceComparator",
    "methods" : [
              {"sl" : 38, "el" : 58, "sc" : 3},  {"sl" : 60, "el" : 69, "sc" : 3}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_3" : {
					  "name" : "unleasedUnaryService",
					  "pass" : true,
					  "methods" : [{"sl": 38 },{"sl": 60 },],
					  "statements" : [{"sl": 40 },{"sl": 41 },{"sl": 48 },{"sl": 49 },{"sl": 51 },{"sl": 61 },{"sl": 65 },]
					  },
		"test_13" : {
					  "name" : "checkRanking",
					  "pass" : true,
					  "methods" : [{"sl": 38 },{"sl": 60 },],
					  "statements" : [{"sl": 40 },{"sl": 41 },{"sl": 48 },{"sl": 49 },{"sl": 51 },{"sl": 57 },{"sl": 61 },{"sl": 65 },]
					  },
		"test_7" : {
					  "name" : "leasedMultiService",
					  "pass" : true,
					  "methods" : [{"sl": 38 },{"sl": 60 },],
					  "statements" : [{"sl": 40 },{"sl": 41 },{"sl": 48 },{"sl": 49 },{"sl": 51 },{"sl": 61 },{"sl": 65 },]
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
  [ 3 , 7 , 13   ] ,
  [  ] ,
  [ 3 , 7 , 13   ] ,
  [ 3 , 7 , 13   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 3 , 7 , 13   ] ,
  [ 3 , 7 , 13   ] ,
  [  ] ,
  [ 3 , 7 , 13   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 13   ] ,
  [  ] ,
  [  ] ,
  [ 3 , 7 , 13   ] ,
  [ 3 , 7 , 13   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 3 , 7 , 13   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] 
];
