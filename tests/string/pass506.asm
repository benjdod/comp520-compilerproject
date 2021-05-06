  0         PUSH         1
  1         LOADL        0
  2         CALL         newarr  
  3         CALL         L21
  4         HALT   (0)   
  5         LOAD         -1[LB]
  6         CALL         putintnl
  7         RETURN (0)   1
  8  L10:   CALL         puteol  
  9         RETURN (0)   0
 10         LOAD         -1[LB]
 11         JUMPIF (0)   L11
 12         LOADL        116
 13         CALL         put     
 14         LOADL        114
 15         CALL         put     
 16         LOADL        117
 17         CALL         put     
 18         LOADL        101
 19         CALL         put     
 20         RETURN (0)   1
 21  L11:   LOADL        102
 22         CALL         put     
 23         LOADL        97
 24         CALL         put     
 25         LOADL        108
 26         CALL         put     
 27         LOADL        115
 28         CALL         put     
 29         LOADL        101
 30         CALL         put     
 31         CALL         puteol  
 32         RETURN (0)   1
 33  L12:   LOAD         -1[LB]
 34         JUMPIF (0)   L13
 35         LOAD         -1[LB]
 36         CALL         putarr  
 37         CALL         puteol  
 38         RETURN (0)   1
 39  L13:   LOADL        110
 40         CALL         put     
 41         LOADL        117
 42         CALL         put     
 43         LOADL        108
 44         CALL         put     
 45         LOADL        108
 46         CALL         put     
 47         CALL         puteol  
 48         RETURN (0)   1
 49         LOAD         -2[LB]
 50         CALL         arraylen
 51         LOAD         -1[LB]
 52         CALL         arraylen
 53         CALL         ne      
 54         JUMPIF (0)   L15
 55  L14:   LOADL        0
 56         RETURN (1)   1
 57  L15:   LOAD         -1[LB]
 58         CALL         arraylen
 59         LOADL        0
 60  L16:   LOAD         4[LB]
 61         LOAD         3[LB]
 62         CALL         lt      
 63         JUMPIF (0)   L17
 64         LOAD         -2[LB]
 65         LOAD         4[LB]
 66         CALL         fieldref
 67         LOAD         -1[LB]
 68         LOAD         4[LB]
 69         CALL         fieldref
 70         CALL         eq      
 71         JUMPIF (0)   L14
 72         CALL         succ    
 73         JUMP         ***
 74  L17:   LOADL        1
 75         RETURN (1)   1
 76         LOAD         -1[LB]
 77         CALL         arraylen
 78         RETURN (1)   0
 79  L18:   LOAD         -2[LB]
 80         LOAD         -1[LB]
 81         CALL         eq      
 82         JUMPIF (0)   L19
 83         LOADL        17
 84         CALL         newarr  
 85         LOAD         -1[ST]
 86         LOADL        0
 87         LOADL        115
 88         CALL         arrayupd
 89         LOAD         -1[ST]
 90         LOADL        1
 91         LOADL        116
 92         CALL         arrayupd
 93         LOAD         -1[ST]
 94         LOADL        2
 95         LOADL        114
 96         CALL         arrayupd
 97         LOAD         -1[ST]
 98         LOADL        3
 99         LOADL        105
100         CALL         arrayupd
101         LOAD         -1[ST]
102         LOADL        4
103         LOADL        110
104         CALL         arrayupd
105         LOAD         -1[ST]
106         LOADL        5
107         LOADL        103
108         CALL         arrayupd
109         LOAD         -1[ST]
110         LOADL        6
111         LOADL        115
112         CALL         arrayupd
113         LOAD         -1[ST]
114         LOADL        7
115         LOADL        32
116         CALL         arrayupd
117         LOAD         -1[ST]
118         LOADL        8
119         LOADL        97
120         CALL         arrayupd
121         LOAD         -1[ST]
122         LOADL        9
123         LOADL        114
124         CALL         arrayupd
125         LOAD         -1[ST]
126         LOADL        10
127         LOADL        101
128         CALL         arrayupd
129         LOAD         -1[ST]
130         LOADL        11
131         LOADL        32
132         CALL         arrayupd
133         LOAD         -1[ST]
134         LOADL        12
135         LOADL        101
136         CALL         arrayupd
137         LOAD         -1[ST]
138         LOADL        13
139         LOADL        113
140         CALL         arrayupd
141         LOAD         -1[ST]
142         LOADL        14
143         LOADL        117
144         CALL         arrayupd
145         LOAD         -1[ST]
146         LOADL        15
147         LOADL        97
148         CALL         arrayupd
149         LOAD         -1[ST]
150         LOADL        16
151         LOADL        108
152         CALL         arrayupd
153         JUMP         L20
154  L19:   LOADL        21
155         CALL         newarr  
156         LOAD         -1[ST]
157         LOADL        0
158         LOADL        115
159         CALL         arrayupd
160         LOAD         -1[ST]
161         LOADL        1
162         LOADL        116
163         CALL         arrayupd
164         LOAD         -1[ST]
165         LOADL        2
166         LOADL        114
167         CALL         arrayupd
168         LOAD         -1[ST]
169         LOADL        3
170         LOADL        105
171         CALL         arrayupd
172         LOAD         -1[ST]
173         LOADL        4
174         LOADL        110
175         CALL         arrayupd
176         LOAD         -1[ST]
177         LOADL        5
178         LOADL        103
179         CALL         arrayupd
180         LOAD         -1[ST]
181         LOADL        6
182         LOADL        115
183         CALL         arrayupd
184         LOAD         -1[ST]
185         LOADL        7
186         LOADL        32
187         CALL         arrayupd
188         LOAD         -1[ST]
189         LOADL        8
190         LOADL        97
191         CALL         arrayupd
192         LOAD         -1[ST]
193         LOADL        9
194         LOADL        114
195         CALL         arrayupd
196         LOAD         -1[ST]
197         LOADL        10
198         LOADL        101
199         CALL         arrayupd
200         LOAD         -1[ST]
201         LOADL        11
202         LOADL        32
203         CALL         arrayupd
204         LOAD         -1[ST]
205         LOADL        12
206         LOADL        110
207         CALL         arrayupd
208         LOAD         -1[ST]
209         LOADL        13
210         LOADL        111
211         CALL         arrayupd
212         LOAD         -1[ST]
213         LOADL        14
214         LOADL        116
215         CALL         arrayupd
216         LOAD         -1[ST]
217         LOADL        15
218         LOADL        32
219         CALL         arrayupd
220         LOAD         -1[ST]
221         LOADL        16
222         LOADL        101
223         CALL         arrayupd
224         LOAD         -1[ST]
225         LOADL        17
226         LOADL        113
227         CALL         arrayupd
228         LOAD         -1[ST]
229         LOADL        18
230         LOADL        117
231         CALL         arrayupd
232         LOAD         -1[ST]
233         LOADL        19
234         LOADL        97
235         CALL         arrayupd
236         LOAD         -1[ST]
237         LOADL        20
238         LOADL        108
239         CALL         arrayupd
240  L20:   CALL         L12
241         RETURN (0)   2
242  L21:   PUSH         1
243         LOADL        3
244         CALL         newarr  
245         LOAD         -1[ST]
246         LOADL        0
247         LOADL        46
248         CALL         arrayupd
249         LOAD         -1[ST]
250         LOADL        1
251         LOADL        46
252         CALL         arrayupd
253         LOAD         -1[ST]
254         LOADL        2
255         LOADL        46
256         CALL         arrayupd
257         STORE        3[LB]
258         PUSH         1
259         LOADL        0
260         STORE        4[LB]
261         LOAD         3[LB]
262         CALL         L12
263         LOAD         4[LB]
264         CALL         L12
265         LOAD         3[LB]
266         LOAD         4[LB]
267         CALL         L18
268         LOAD         3[LB]
269         STORE        4[LB]
270         CALL         L10
271         LOAD         3[LB]
272         CALL         L12
273         LOAD         4[LB]
274         CALL         L12
275         LOAD         3[LB]
276         LOAD         4[LB]
277         CALL         L18
278         RETURN (0)   1
