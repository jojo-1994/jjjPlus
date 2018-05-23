package com.sz.jjj.rxjavaretrofitdemo;

import java.util.List;

/**
 * @author:jjj
 * @data:2018/5/21
 * @description:
 */

public class Movie {

    /**
     * count : 2
     * start : 0
     * total : 250
     * subjects : [{"rating":{"max":10,"average":9.6,"stars":"50","min":0},"genres":["犯罪","剧情"],"title":"肖申克的救赎","casts":[{"alt":"https://movie.douban.com/celebrity/1054521/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.jpg"},"name":"蒂姆·罗宾斯","id":"1054521"},{"alt":"https://movie.douban.com/celebrity/1054534/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34642.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34642.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34642.jpg"},"name":"摩根·弗里曼","id":"1054534"},{"alt":"https://movie.douban.com/celebrity/1041179/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p5837.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p5837.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p5837.jpg"},"name":"鲍勃·冈顿","id":"1041179"}],"collect_count":1286396,"original_title":"The Shawshank Redemption","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1047973/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.jpg"},"name":"弗兰克·德拉邦特","id":"1047973"}],"year":"1994","images":{"small":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p480747492.jpg","large":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p480747492.jpg","medium":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p480747492.jpg"},"alt":"https://movie.douban.com/subject/1292052/","id":"1292052"}]
     */

    private int count;
    private int start;
    private int total;
    private List<SubjectsBean> subjects;

    public void setCount(int count) {
        this.count = count;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setSubjects(List<SubjectsBean> subjects) {
        this.subjects = subjects;
    }

    public int getCount() {
        return count;
    }

    public int getStart() {
        return start;
    }

    public int getTotal() {
        return total;
    }

    public List<SubjectsBean> getSubjects() {
        return subjects;
    }

    public static class SubjectsBean {
        /**
         * rating : {"max":10,"average":9.6,"stars":"50","min":0}
         * genres : ["犯罪","剧情"]
         * title : 肖申克的救赎
         * casts : [{"alt":"https://movie.douban.com/celebrity/1054521/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.jpg"},"name":"蒂姆·罗宾斯","id":"1054521"},{"alt":"https://movie.douban.com/celebrity/1054534/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34642.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34642.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34642.jpg"},"name":"摩根·弗里曼","id":"1054534"},{"alt":"https://movie.douban.com/celebrity/1041179/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p5837.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p5837.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p5837.jpg"},"name":"鲍勃·冈顿","id":"1041179"}]
         * collect_count : 1286396
         * original_title : The Shawshank Redemption
         * subtype : movie
         * directors : [{"alt":"https://movie.douban.com/celebrity/1047973/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.jpg"},"name":"弗兰克·德拉邦特","id":"1047973"}]
         * year : 1994
         * images : {"small":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p480747492.jpg","large":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p480747492.jpg","medium":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p480747492.jpg"}
         * alt : https://movie.douban.com/subject/1292052/
         * id : 1292052
         */

        private RatingBean rating;
        private String title;
        private int collect_count;
        private String original_title;
        private String subtype;
        private String year;
        private ImagesBean images;
        private String alt;
        private String id;
        private List<String> genres;
        private List<CastsBean> casts;
        private List<DirectorsBean> directors;

        public void setRating(RatingBean rating) {
            this.rating = rating;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setCollect_count(int collect_count) {
            this.collect_count = collect_count;
        }

        public void setOriginal_title(String original_title) {
            this.original_title = original_title;
        }

        public void setSubtype(String subtype) {
            this.subtype = subtype;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public void setImages(ImagesBean images) {
            this.images = images;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setGenres(List<String> genres) {
            this.genres = genres;
        }

        public void setCasts(List<CastsBean> casts) {
            this.casts = casts;
        }

        public void setDirectors(List<DirectorsBean> directors) {
            this.directors = directors;
        }

        public RatingBean getRating() {
            return rating;
        }

        public String getTitle() {
            return title;
        }

        public int getCollect_count() {
            return collect_count;
        }

        public String getOriginal_title() {
            return original_title;
        }

        public String getSubtype() {
            return subtype;
        }

        public String getYear() {
            return year;
        }

        public ImagesBean getImages() {
            return images;
        }

        public String getAlt() {
            return alt;
        }

        public String getId() {
            return id;
        }

        public List<String> getGenres() {
            return genres;
        }

        public List<CastsBean> getCasts() {
            return casts;
        }

        public List<DirectorsBean> getDirectors() {
            return directors;
        }

        public static class RatingBean {
            /**
             * max : 10
             * average : 9.6
             * stars : 50
             * min : 0
             */

            private int max;
            private double average;
            private String stars;
            private int min;

            public void setMax(int max) {
                this.max = max;
            }

            public void setAverage(double average) {
                this.average = average;
            }

            public void setStars(String stars) {
                this.stars = stars;
            }

            public void setMin(int min) {
                this.min = min;
            }

            public int getMax() {
                return max;
            }

            public double getAverage() {
                return average;
            }

            public String getStars() {
                return stars;
            }

            public int getMin() {
                return min;
            }
        }

        public static class ImagesBean {
            /**
             * small : https://img3.doubanio.com/view/photo/s_ratio_poster/public/p480747492.jpg
             * large : https://img3.doubanio.com/view/photo/s_ratio_poster/public/p480747492.jpg
             * medium : https://img3.doubanio.com/view/photo/s_ratio_poster/public/p480747492.jpg
             */

            private String small;
            private String large;
            private String medium;

            public void setSmall(String small) {
                this.small = small;
            }

            public void setLarge(String large) {
                this.large = large;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }

            public String getSmall() {
                return small;
            }

            public String getLarge() {
                return large;
            }

            public String getMedium() {
                return medium;
            }
        }

        public static class CastsBean {
            /**
             * alt : https://movie.douban.com/celebrity/1054521/
             * avatars : {"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.jpg"}
             * name : 蒂姆·罗宾斯
             * id : 1054521
             */

            private String alt;
            private AvatarsBean avatars;
            private String name;
            private String id;

            public void setAlt(String alt) {
                this.alt = alt;
            }

            public void setAvatars(AvatarsBean avatars) {
                this.avatars = avatars;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getAlt() {
                return alt;
            }

            public AvatarsBean getAvatars() {
                return avatars;
            }

            public String getName() {
                return name;
            }

            public String getId() {
                return id;
            }

            public static class AvatarsBean {
                /**
                 * small : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.jpg
                 * large : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.jpg
                 * medium : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.jpg
                 */

                private String small;
                private String large;
                private String medium;

                public void setSmall(String small) {
                    this.small = small;
                }

                public void setLarge(String large) {
                    this.large = large;
                }

                public void setMedium(String medium) {
                    this.medium = medium;
                }

                public String getSmall() {
                    return small;
                }

                public String getLarge() {
                    return large;
                }

                public String getMedium() {
                    return medium;
                }
            }
        }

        public static class DirectorsBean {
            /**
             * alt : https://movie.douban.com/celebrity/1047973/
             * avatars : {"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.jpg"}
             * name : 弗兰克·德拉邦特
             * id : 1047973
             */

            private String alt;
            private AvatarsBean avatars;
            private String name;
            private String id;

            public void setAlt(String alt) {
                this.alt = alt;
            }

            public void setAvatars(AvatarsBean avatars) {
                this.avatars = avatars;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getAlt() {
                return alt;
            }

            public AvatarsBean getAvatars() {
                return avatars;
            }

            public String getName() {
                return name;
            }

            public String getId() {
                return id;
            }

            public static class AvatarsBean {
                /**
                 * small : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.jpg
                 * large : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.jpg
                 * medium : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.jpg
                 */

                private String small;
                private String large;
                private String medium;

                public void setSmall(String small) {
                    this.small = small;
                }

                public void setLarge(String large) {
                    this.large = large;
                }

                public void setMedium(String medium) {
                    this.medium = medium;
                }

                public String getSmall() {
                    return small;
                }

                public String getLarge() {
                    return large;
                }

                public String getMedium() {
                    return medium;
                }
            }
        }
    }
}
