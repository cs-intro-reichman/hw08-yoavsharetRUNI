/** Represnts a list of musical tracks. The list has a maximum capacity (int),
 *  and an actual size (number of tracks in the list, an int). */
class PlayList {
    private Track[] tracks;  // Array of tracks (Track objects)   
    private int maxSize;     // Maximum number of tracks in the array
    private int size;        // Actual number of tracks in the array

    /** Constructs an empty play list with a maximum number of tracks. */ 
    public PlayList(int maxSize) {
        this.maxSize = maxSize;
        tracks = new Track[maxSize];
        size = 0;
    }

    /** Returns the maximum size of this play list. */ 
    public int getMaxSize() {
        return maxSize;
    }
    
    /** Returns the current number of tracks in this play list. */ 
    public int getSize() {
        return size;
    }

    /** Method to get a track by index */
    public Track getTrack(int index) {
        if (index >= 0 && index < size) {
            return tracks[index];
        } else {
            return null;
        }
    }
    
    /** Appends the given track to the end of this list. 
     *  If the list is full, does nothing and returns false.
     *  Otherwise, appends the track and returns true. */
    public boolean add(Track track) {
        if(this.size == this.maxSize){
            // The list is full
            return false;
        }
        this.tracks[size] = track;
        size++;
        return true;
    }

    /** Returns the data of this list, as a string. Each track appears in a separate line. */
    //// For an efficient implementation, use StringBuilder.
    public String toString() {
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < this.size; i++){
            str.append("\n" + this.tracks[i].toString());
        }
        return str.toString();
    }

    /** Removes the last track from this list. If the list is empty, does nothing. */
     public void removeLast() {
        if(size == 0){
            return;
        }
        this.tracks[size--] = null;
    }
    
    /** Returns the total duration (in seconds) of all the tracks in this list.*/
    public int totalDuration() {
        int totalDuration = 0;
        for(int i = 0; i<this.size; i++){
            totalDuration += this.tracks[i].getDuration();
        }
        return totalDuration;
    }

    /** Returns the index of the track with the given title in this list.
     *  If such a track is not found, returns -1. */
    public int indexOf(String title) {
        for(int i = 0; i<this.size; i++){
            if(this.tracks[i].getTitle().toLowerCase().equals(title.toLowerCase())){
                return i;
            }
        }
        // track was not founf in the list
        return -1;
    }

    /** Inserts the given track in index i of this list. For example, if the list is
     *  (t5, t3, t1), then just after add(1,t4) the list becomes (t5, t4, t3, t1).
     *  If the list is the empty list (), then just after add(0,t3) it becomes (t3).
     *  If i is negative or greater than the size of this list, or if the list
     *  is full, does nothing and returns false. Otherwise, inserts the track and
     *  returns true. */
    public boolean add(int i, Track track) {
        if(i < 0 || i > this.size || this.size == this.maxSize) {
            return false;
        }
        this.shiftRight(i);
        this.tracks[i] = track;
        this.size++;
        return true;   
    }
     
    /** Removes the track in the given index from this list.
     *  If the list is empty, or the given index is negative or too big for this list, 
     *  does nothing and returns -1. */
    public int remove(int i) {
        if(this.size == 0 || i < 0 || i > this.size){
            return -1;
        }
        this.tracks[i] = null;
        this.shiftLeft(i);
        this.size--;
        return 1;
    }

    /** Removes the first track that has the given title from this list.
     *  If such a track is not found, or the list is empty, or the given index
     *  is negative or too big for this list, does nothing. */
    public void remove(String title) {
        this.remove(this.indexOf(title));
    }

    /** Removes the first track from this list. If the list is empty, does nothing. */
    public void removeFirst() {
        this.remove(0);
    }
    
    /** Adds all the tracks in the other list to the end of this list. 
     *  If the total size of both lists is too large, does nothing. */
    //// An elegant and terribly inefficient implementation.
     public void add(PlayList other) {
        if((this.size + other.size) > this.maxSize){
            return;
        }
        for(int i = 0; i<other.size; i++){
            this.add(other.getTrack(i));
        }
    }

    /** Returns the index in this list of the track that has the shortest duration,
     *  starting the search in location start. For example, if the durations are 
     *  7, 1, 6, 7, 5, 8, 7, then min(2) returns 4, since this the index of the 
     *  minimum value (5) when starting the search from index 2.  
     *  If start is negative or greater than size - 1, returns -1.
     */
    private int minIndex(int start) {
        if(start < 0 || start > (this.size - 1)){
            return -1;
        }

        int minIdx = start, minVal = this.tracks[minIdx].getDuration(); 
        for(int i = (start + 1); i < this.size; i++){
            if(this.tracks[i].getDuration() < minVal){
                minVal = this.tracks[i].getDuration();
                minIdx = i;
            }
        }
        return minIdx;
    }

    /** Returns the title of the shortest track in this list. 
     *  If the list is empty, returns null. */
    public String titleOfShortestTrack() {
        return tracks[minIndex(0)].getTitle();
    }

    /** Sorts this list by increasing duration order: Tracks with shorter
     *  durations will appear first. The sort is done in-place. In other words,
     *  rather than returning a new, sorted playlist, the method sorts
     *  the list on which it was called (this list). */
    public void sortedInPlace() {
        for(int i = 0; i < (this.size - 1); i++){
           swap(i, minIndex(i));
        }
    }

    /*** Help Functions ***/
    /**
     * Shifting right all the tracks from a given index 
     * @param index shift start index
     */
    private void shiftRight(int index){
        if(this.size == this.maxSize){
            // No room for shifting right
            return;
        }

        for(int i = this.size; i >= index; i--){
            this.tracks[i+1] = this.tracks[i];
        }
    }

    /**
     * Shifting left all the tracks from a given index 
     * @param index shift start index
     */
    private void shiftLeft(int index){
        for(int i = index; i < this.size; i++){
            this.tracks[i] = this.tracks[i+1];
        }
    }

    /**
     * Swapps two tracks
     * @param idx1
     * @param idx2
     */
    private void swap(int idx1, int idx2){
        Track temp = this.tracks[idx1];
        this.tracks[idx1] = this.tracks[idx2];
        this.tracks[idx2] = temp; 
    }
}
