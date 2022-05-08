import StatelessWidget from '@/components/StatelessWidget';

function NotFound() {
    return (
        <StatelessWidget
            type="page"
            size="large"
            title="Ups, page not found..."
            description="Please, try to find somewhere else :)"
        />
    );
}

export default NotFound;
