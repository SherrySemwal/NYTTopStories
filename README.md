# NYTTopStories

Solution design:
- I have used MVVM, single activity architecture with repository pattern to make API calls and kept result in live data
(exception or response) ,then live data is observed in the fragment to update the UI according to the latest data fetched.
- ApiResult class is used to wrap the result from the api.
- Hilt is added rather than Dagger 2 because it’s less complex compared to Dagger2 and removes lot of boilerplate code
like Component class, Activity or Fragment injection. Although Hilt is an upper layer built on Dagger.
- Have written JUnit test cases for API logic.
- App will work in both portrait and landscape mode.

Future Updated points:
- Database can be added for caching purposes
- Refresh layout can be added for refreshing the data
- NetworkState can be added to check continuously that device is connected to the internet or not.
- Base structure can be added for Activity and Fragment.
- Kotlin Flow will be used for better reactive approach.
- SnackBar can be added for showing error rather than toastSolution design.
