#include <bits/stdc++.h>
using namespace std;

typedef long long ll;
typedef vector<int> vi;
typedef vector<vi> vvi;
typedef vector<string> vs;

#define inf 1061109567
#define pb push_back
#define mp make_pair
#define all(a) a.begin(),a.end()
#define mem(x,a) memset(x,a,sizeof(x))
#define rep(i,n) for(int i(0),_n(n);i<_n;++i)
#define repi(i,a,b) for(int i(a),_b(b);i<=_b;++i)
#define repr(i,a,b) for(int i(a),_b(b);i>=_b;--i)
#define repe(it,c) for(__typeof((c).begin()) it=(c).begin();it!=(c).end();++it)
#define len(x) ((int)(x.size()))
#define DEBUG 1
#if DEBUG && !ONLINE_JUDGE
	#define debug(args...) (Debugger()) , args
	class Debugger { public: Debugger(const std::string& _separator = ", ") : first(true), separator(_separator){} template<typename ObjectType> Debugger& operator , (const ObjectType& v) { if(!first) std::cerr << separator; std::cerr << v; first = false; return *this; } ~Debugger() { std::cerr << endl;} private: bool first; std::string separator; }; template <typename T1, typename T2> inline std::ostream& operator << (std::ostream& os, const std::pair<T1, T2>& p) { return os << "(" << p.first << ", " << p.second << ")"; } template<typename T> inline std::ostream &operator << (std::ostream & os,const std::vector<T>& v) { bool first = true; os << "["; for(unsigned int i = 0; i < v.size(); i++) { if(!first) os << ", "; os << v[i]; first = false; } return os << "]"; } template<typename T> inline std::ostream &operator << (std::ostream & os,const std::set<T>& v) { bool first = true; os << "["; for (typename std::set<T>::const_iterator ii = v.begin(); ii != v.end(); ++ii) { if(!first) os << ", "; os << *ii; first = false; } return os << "]"; } template<typename T1, typename T2> inline std::ostream &operator << (std::ostream & os,const std::map<T1, T2>& v) { bool first = true; os << "["; for (typename std::map<T1, T2>::const_iterator ii = v.begin(); ii != v.end(); ++ii) { if(!first) os << ", "; os << *ii ; first = false; } return os << "]"; }
#else
		#define debug(args...)
#endif


int w,h,temp;
int g1x,g2x,s1x,s2x,t1x,t2x;
int g1y,g2y,s1y,s2y,t1y,t2y;
string s;
vs v;
vs tg1,tg2;





int main(){
	ios_base::sync_with_stdio(0);

	srand(time(0));

	cin>>w>>h;
	rep(i,w){
		cin>>s;
		v.pb(s);
	}

	rep(i,4){
		if(rand()%2){
			cout<<"v ";
		}
		else {
			cout<<"h ";
			cout<<rand()%h<<" "<<rand()%w<<endl;
		}
	}
    cout<<rand()%h<<" "<<rand()%w<<endl;
    cout<<rand()%h<<" "<<rand()%w<<endl;

    cin>>g1x>>g1y;
    cin>>g2x>>g2y;
    cin>>s1x>>s1y;
    cin>>s2x>>s2y;
    cin>>t1x>>t1y;
    cin>>t2x>>t2y;
    v.clear();
    rep(i,h){
        cin>>s;
        v.pb(s);
    }

    tg1.clear();
    tg2.clear();
    rep(i,3){
        cin>>s;
        tg1.pb(s);
    }

    rep(i,3){
        cin>>s;
        tg2.pb(s);
    }
//output gunship 1
    temp=rand()%3;
    if(temp==0){
        cout<<"M "<<rand()%3-1<<endl;
    }
    else if(temp==1){
        cout<<"F "<<rand()%40<<" "<<rand()%40<<endl;
    }
    else if (temp==2) {
        cout<<"B "<<rand()%40<<" "<<rand()%40<<endl;
    }
//ouput gunship 2
    temp=rand()%3;
    if(temp==0){
        cout<<"M "<<rand()%3-1<<endl;
    }
    else if(temp==1){
        cout<<"F "<<rand()%40<<" "<<rand()%40<<endl;
    }
    else if (temp==2) {
        cout<<"B "<<rand()%40<<" "<<rand()%40<<endl;
    }
    cout<<"M "<<rand()%3-1<<endl;

//output submarine 1
    temp=rand()%2;
    if(temp==0){
        cout<<"M "<<rand()%3-1<<endl;
    }
    else if(temp==1){
        cout<<"F "<<rand()%40<<" "<<rand()%40<<endl;
    }
//ouput submarine 2
    temp=rand()%2;
    if(temp==0){
        cout<<"M "<<rand()%3-1<<endl;
    }
    else if(temp==1){
        cout<<"F "<<rand()%40<<" "<<rand()%40<<endl;
    }
    cout<<"M "<<rand()%3-1<<endl;

//output tagboat 1
    temp=rand()%5;

    if(temp==0){
        cout<<"S"<<endl;
    }
    else if (temp==1){
        cout<<"R"<<endl;
    }
    else if (temp==2){
        cout<<"L"<<endl;
    }
    else if (temp==3){
        cout<<"D"<<endl;
    }
    else if (temp==4){
        cout<<"U"<<endl;
    }
//output tagboat 2
    temp=rand()%5;
    if(temp==0){
        cout<<"S"<<endl;
    }
    else if (temp==1){
        cout<<"R"<<endl;
    }
    else if (temp==2){
        cout<<"L"<<endl;
    }
    else if (temp==3){
        cout<<"D"<<endl;
    }
    else if (temp==4){
        cout<<"U"<<endl;
    }

}








