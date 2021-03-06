#include<bits/stdc++.h>

using namespace std;

typedef long long ll;
typedef unsigned long long ull;
typedef pair<int,int> pii;
typedef vector<int> vi;
typedef vector<long long> vll;
typedef vector<vector<int> > vvi;
typedef vector<string> vs;
typedef vector<pair<int,int> > vpii;

#define pb push_back
#define mp make_pair
#define PI acos(-1)
#define all(a) (a).begin(),(a).end()
#define len(a) ((int)(a).size())
#define mem(a,n) memset(a,n,sizeof(a))
#define eps 1e-9
#define rep(i,n) for(int i=0;i<(n);i++)
#define repi(i,a,n) for(int i=(a);i<(n);i++)
#define repr(i,a,n) for(int i=(n);i>=(a);i--)


#define max_x 20
#define max_y 20
#define tot_mov 100


ofstream fout;

int w,h;

string tugmove = "LRUDS";

void getrt1(){
	int fcount;
	cin>>fcount;
	fout<<"-------------------------------"<<endl;
	fout<<"rt1"<<endl;
	fout<<fcount<<endl;
	int t1,t2;
	rep(i,fcount){
		cin>>t1>>t2;
		fout<<t1<<" "<<t2<<endl;
	}
	fout<<"oppo tugboats"<<endl;
	rep(i,2){
		fout<<"tugi->"<<i<<endl;
		cin>>t1>>t2;
		fout<<t1<<" "<<t2<<endl;
	}
}

int cur=0;
vpii v;

void move(){
	fout<<"**** MOVE ***"<<endl;
	rep(i,2){
		//~ int t = rand()%4,x = rand()%20+20,y=rand()%20;
		int t = 0,x = rand()%20+20,y=rand()%20;
		if(t==0){
			switch(rand()%3){
				case 0:
					cout<<"M 0"<<endl;
					fout<<"M 0"<<endl;
					break;
				case 1:
					cout<<"M 1"<<endl;
					fout<<"M 1"<<endl;
					break;
				case 2:
					cout<<"M -1"<<endl;
					fout<<"M -1"<<endl;
					break;
			}
		}else if(t==1){
			int a = cur%len(v);
			cur++;
			cout<<"F "<<v[a].first<<" "<<v[a].second<<endl;
			fout<<"F "<<v[a].first<<" "<<v[a].second<<endl;
		}else if(t==2){
			cout<<"B "<<x<<" "<<y<<endl;
			fout<<"B "<<x<<" "<<y<<endl;
		}else{
			cout<<"I"<<endl;
			fout<<"I"<<endl;
		}
	}
	
	//for submarines
	rep(i,2){
		//~ if(rand()%2){
		if(1){
			switch(rand()%4){
			//~ switch(3){
				case 0:
					cout<<"M 0"<<endl;
					fout<<"M 0"<<endl;
					break;
				case 1:
					cout<<"M 1"<<endl;
					fout<<"M 1"<<endl;
					break;
				case 2:
					cout<<"M -1"<<endl;
					fout<<"M -1"<<endl;
					break;
				case 3:
					cout<<"I"<<endl;
					fout<<"I"<<endl;
					break;
			}
		}else{
			int a = rand()%len(v);
			cout<<"F "<<v[a].first<<" "<<v[a].second<<endl;
			fout<<"F "<<v[a].first<<" "<<v[a].second<<endl;
		}
		
	}
	
	//for tugboat
	rep(i,2){
		//~ int t = rand()%5;
		int t = 1;
		cout<<tugmove[t]<<endl;
		fout<<tugmove[t]<<endl;
	}
}

void getrt2(){
	int fcount;
	cin>>fcount;
	fout<<"-------------------------------"<<endl;
	fout<<"rt2"<<endl;
	fout<<fcount<<endl;
	int t1,t2;
	rep(i,fcount){
		char ch;
		cin>>ch>>t1>>t2;
		fout<<ch<<" "<<t1<<" "<<t2<<endl;
	}
	
	//get ship positions
	rep(i,6){
		cin>>t1>>t2;
		fout<<t1<<" "<<t2<<endl;
	}
	
	//get board 
	rep(i,max_y){
		string s;
		cin>>s;
		fout<<s<<endl;
	}
	
	//get tugboat visibility square
	rep(i,2){
		rep(j,3){
			string s;
			cin>>s;
			fout<<s<<endl;
		}
	}
}

int main(void){
	rep(i,5)rep(j,6)v.pb(mp(39-i,19-j));

	
	
	ios_base::sync_with_stdio(0);
	fout.open("log1",ofstream::out);
	cin>>w>>h;
	fout<<w<<" "<<h<<endl;
	string s;
	rep(i,h){
		cin>>s;
		fout<<s<<endl;
	}
	
	srand(time(0));
	rep(i,4){
		int a=rand()%max_x,b=rand()%max_y;
		if(rand()%2){
			cout<<"h "<<a<<" "<<b<<endl;
			fout<<"h "<<a<<" "<<b<<endl;
		}else{
			cout<<"v "<<a<<" "<<b<<endl;
			fout<<"v "<<a<<" "<<b<<endl;
		}
	}
	//~ rep(i,2){
		//~ int a=rand()%max_x,b=rand()%max_y;
		//~ cout<<a<<" "<<b<<endl;
		//~ fout<<a<<" "<<b<<endl;
	//~ }
	cout<<"19 19\n19 18"<<endl;
	fout<<"19 19\n19 18"<<endl;
	
	rep(i,tot_mov){
		fout<<"\n---------------------------------"<<endl;
		fout<<"Move: "<<i<<endl;
		getrt1();
		move();
		getrt2();
	}
	
	return 0;
}
